package com.grupc.userms.services;

import com.grupc.userms.entities.User;
import com.grupc.userms.exception.EmptyInputException;
import com.grupc.userms.exception.UniqueFieldException;
import com.grupc.userms.exception.UserException;
import com.grupc.userms.exception.UserNotFoundException;
import com.grupc.userms.model.request.CreateUserRequest;
import com.grupc.userms.model.request.UpdateUserRequest;
import com.grupc.userms.repositories.UserRepository;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser (CreateUserRequest request) throws UserException {
        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getFullName())) {
            throw new EmptyInputException();
        }
        if( !isEMailUnique(request.getEmail())){
            throw new UniqueFieldException();
        }
        User createdUser = new User(request.getFullName(), request.getEmail());
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

    public User updateUser (UpdateUserRequest request) throws UserException{
        User updatedUser = getUserById(request.getId());

        if (StringUtils.isNotBlank(request.getEmail())){
            if( !isEMailUnique(request.getEmail())){
                throw new UniqueFieldException();
            }
            updatedUser.setEMail(request.getEmail());
        }

        if (StringUtils.isNotBlank(request.getFullName())){
            updatedUser.setFullName(request.getFullName());
        }

        return userRepository.save(updatedUser);
    }

    public User updateUserName (UpdateUserRequest request){

       User updatedUser = getUserById(request.getId()) ;

       if (StringUtils.isNotBlank(request.getFullName())){
           updatedUser.setFullName(request.getFullName());
           return userRepository.save(updatedUser);
       }
       throw new EmptyInputException();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
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
