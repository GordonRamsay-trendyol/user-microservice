package com.grupc.userms.services;

import com.alibaba.fastjson.JSON;
import com.grupc.userms.entities.User;
import com.grupc.userms.exception.EmptyInputException;
import com.grupc.userms.exception.UniqueFieldException;
import com.grupc.userms.exception.UserException;
import com.grupc.userms.exception.UserNotFoundException;
import com.grupc.userms.dto.request.CreateUserRequest;
import com.grupc.userms.dto.request.UpdateUserRequest;
import com.grupc.userms.repositories.UserRepository;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import com.grupc.userms.configuration.KafkaProducerConfiguration;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String UPDATE_TOPIC = "user.update";
    private static final String CREATE_TOPIC = "user.create";
    private static final String DELETE_TOPIC = "user.delete";

    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserService(UserRepository userRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public User addUser(CreateUserRequest request) throws UserException {
        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getFullName())) {
            throw new EmptyInputException();
        }
        checkEmailUnique(request.getEmail());

        User createdUser = new User(request.getFullName(), request.getEmail());
        kafkaTemplate.send(CREATE_TOPIC, JSON.toJSONString(createdUser, false));
        return userRepository.save(createdUser);
    }

    public void deleteUser(User user) throws UserException {
        User userToDelete = getUserById(user.getId());
        if (userToDelete != null) {
            userRepository.delete(user);
            kafkaTemplate.send(DELETE_TOPIC, JSON.toJSONString(user, false));
            return;
        }
        throw new UserNotFoundException();
    }

    public User updateUser(UpdateUserRequest request) throws UserException {
        User updatedUser = getUserById(request.getId());

        String email = request.getEmail();

        if (StringUtils.isNotBlank(email)) {
            checkEmailUnique(email);
            updatedUser.setEmail(email);
        }

        if (StringUtils.isNotBlank(request.getFullName())) {
            updatedUser.setFullName(request.getFullName());
        }
        kafkaTemplate.send(UPDATE_TOPIC, JSON.toJSONString(updatedUser, false));
        return userRepository.save(updatedUser);
    }

    public User updateUserName(UpdateUserRequest request) {

        User updatedUser = getUserById(request.getId());
        if (StringUtils.isBlank(request.getFullName())) {
            throw new EmptyInputException();
        }

        updatedUser.setFullName(request.getFullName());
        kafkaTemplate.send(UPDATE_TOPIC, JSON.toJSONString(updatedUser, false));
        return userRepository.save(updatedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws EmptyInputException {
        return userRepository.findById(id).orElseThrow(EmptyInputException::new);
    }

    public void checkEmailUnique(@NotNull String email) throws UniqueFieldException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UniqueFieldException();
        }
    }
}
