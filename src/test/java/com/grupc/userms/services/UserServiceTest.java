package com.grupc.userms.services;

import com.grupc.userms.entities.User;
import com.grupc.userms.exception.EmptyInputException;

import com.grupc.userms.exception.UserNotFoundException;
import com.grupc.userms.model.request.CreateUserRequest;
import com.grupc.userms.model.request.UpdateUserRequest;
import com.grupc.userms.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Before
    public void createMocks()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_validRequestGiven_returnUser() {
        User createdUser = new User(1L,"test@testmail.com", "testUser");
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("testUser");
        request.setEmail("test@hotmail.com");

        Mockito.when(userRepository.save(any())).thenReturn(createdUser);

        User foundUser = userService.addUser(request);

        assertEquals(createdUser, foundUser);
        
    }

    @Test
    void addUser_notValidRequestGiven_returnError(){
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName("testUser");

        Exception exception = assertThrows(EmptyInputException.class, () -> userService.addUser(request));
        assertEquals(EmptyInputException.class, exception.getClass());

    }

    @Test
    void deleteUser_validUserGiven_checkUserRepoDeleteAccessedAtLeastOneTime() {
        User createdUser = new User(1L,"test@testmail.com", "testUser");
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(createdUser));
        userService.deleteUser(createdUser);
        Mockito.verify(userRepository,Mockito.times(1)).delete(eq(createdUser));
    }

    @Test
    void deleteUser_notValidUserGiven_ReturnError(){
        User createdUser = new User(1L,"test@testmail.com", "testUser");
        Exception exception = assertThrows(UserNotFoundException.class, ()-> userService.deleteUser(createdUser));
        assertEquals(UserNotFoundException.class,exception.getClass());
    }

    @Test
    void updateUser_validInputGiven_ExpectSuccess() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(1L);
        request.setEmail("test@testmail.com");
        request.setFullName("testUser");
        User user = new User(1L,"test@testmail.com", "test");
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User updatedUser = userService.updateUser(request);
        assertEquals("testUser",updatedUser.getFullName());
    }

    @Test
    void updateUserName() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(1L);
        request.setFullName("testUser");
        User user = new User(1L,"test@testmail.com", "user");
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User updatedUser = userService.updateUserName(request);
        assertEquals("testUser",updatedUser.getFullName());
    }

    @Test
    void updateUser_NotValidUserIdGiven(){
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(1L);
        request.setEmail("test@testmail.com");
        request.setFullName("testUser");

        Exception exception = assertThrows(EmptyInputException.class,()->userService.updateUser(request));
        assertEquals(EmptyInputException.class, exception.getClass());
    }

    @Test
    void getUserById_ValidIdGiven_ShouldReturnUser() {
        User createdUser = new User(1L,"test@testmail.com", "testUser");
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(createdUser));
        User foundUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser,foundUser);
    }

   @Test
   void getUserById_NotValidIdGiven_ShouldShowErrorMessage (){
        Throwable throwable = assertThrows(Throwable.class, () -> userService.getUserById(1L));
        assertEquals(EmptyInputException.class,throwable.getClass());
   }

}