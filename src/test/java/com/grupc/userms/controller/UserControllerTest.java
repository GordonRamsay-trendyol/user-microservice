package com.grupc.userms.controller;

import com.grupc.userms.entities.User;
import com.grupc.userms.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "user1@gmail.com","user1"));
        this.userList.add(new User(2L, "user2@gmail.com","user2"));
        this.userList.add(new User(3L, "user3@gmail.com","user3"));
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserName() {
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(userList.size())));
    }


    @Test
    void deleteUser() throws Exception { // ??? returns 400 expected 200 OK
        Long userId = 1L;
        User user = new User(userId, "testUser", "test@testmail.com");
        Mockito.when(userService.getUserById(userId)).thenReturn(user);
        Mockito.doNothing().when(userService).deleteUser(user);

        this.mockMvc.perform(delete("/users", user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is(user.getFullName())))
                .andExpect(jsonPath("$.email", is(user.getEMail())));

    }
}