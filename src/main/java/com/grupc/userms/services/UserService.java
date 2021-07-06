package com.grupc.userms.services;

import com.grupc.userms.entities.User;
import com.grupc.userms.exception.EmptyInputException;
import com.grupc.userms.exception.UniqueFieldException;
import com.grupc.userms.exception.UserException;
import com.grupc.userms.exception.UserNotFoundException;
import com.grupc.userms.repositories.UserRepository;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser (User user) throws UserException {
        if (StringUtils.isBlank(user.getEMail()) || StringUtils.isBlank(user.getFullName())) {
            throw new EmptyInputException();
        }
        if( !isEMailUnique(user.getEMail())){
            throw new UniqueFieldException();
        }
        User createdUser = new User(user.getFullName(), user.getEMail());
        return userRepository.save(createdUser);
    }

    public void deleteUser (User user) throws  UserException{
        Optional<User> userToDelete =  userRepository.findById(user.getId());
        if(userToDelete.isPresent()){
            userRepository.delete(user);
            return;
        }
        throw new UserNotFoundException();
    }

    public User updateUser (User user) throws UserException{
        User updatedUser = getUserById(user.getId());

        if (StringUtils.isNotBlank(user.getEMail())){
            if( !isEMailUnique(user.getEMail())){
                throw new UniqueFieldException();
            }
            updatedUser.setEMail(user.getEMail());
        }

        if (StringUtils.isNotBlank(user.getFullName())){
            updatedUser.setFullName(user.getFullName());
        }

        return userRepository.save(updatedUser);
    }

    public User updateUserName (User user){

       User updatedUser = getUserById(user.getId()) ;

       if (StringUtils.isNotBlank(user.getFullName())){
           updatedUser.setFullName(user.getFullName());
           return userRepository.save(updatedUser);
       }
       throw new EmptyInputException();
    }

    public User getUserById(Long id) throws EmptyInputException{
        Optional<User> optionalUser = userRepository.findById(id); //
        if (optionalUser.isEmpty()){
            throw new EmptyInputException();
        }
        return optionalUser.get();
    }

    public Boolean isEMailUnique (@NotNull String eMail) {
        Optional<User> user = userRepository.findByeMail(eMail);
        return user.isEmpty();
    }
}
