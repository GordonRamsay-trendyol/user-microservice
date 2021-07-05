package com.grupc.userms.services;

import com.grupc.userms.entities.User;
import com.grupc.userms.exception.UserException;
import com.grupc.userms.repositories.UserRepository;
import com.sun.istack.NotNull;
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
        if (user.getEMail() == null || user.getFullName() == null) {
            throw new UserException("Fields cannot be null");
        }
        if( !isEMailUnique(user.getEMail())){
            throw new UserException("e-mail must be unique");
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
        throw new UserException("User cannot be found");

    }

    public User updateUser (User user) throws UserException{

        Optional<User> optionalUpdatedUser = userRepository.findById(user.getId());
        if (optionalUpdatedUser.isEmpty()){
            throw new UserException("id cannot be null");
        }
        User updatedUser = optionalUpdatedUser.get();
        if (user.getEMail() != null){
            if( !isEMailUnique(user.getEMail())){
                throw new UserException("e-mail must be unique");
            }
            updatedUser.setEMail(user.getEMail());
        }
        if (user.getFullName() != null){
            updatedUser.setFullName(user.getFullName());
        }

        return userRepository.save(updatedUser);
    }

    public Boolean isEMailUnique (@NotNull String eMail) {
        Optional<User> user = userRepository.findByeMail(eMail);
        return user.isEmpty();
    }

}
