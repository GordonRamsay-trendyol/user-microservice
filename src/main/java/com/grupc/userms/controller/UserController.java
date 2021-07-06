package com.grupc.userms.controller;

import com.grupc.userms.entities.User;
import com.grupc.userms.exception.UserException;
import com.grupc.userms.services.UserService;
import com.grupc.userms.util.DataResult;
import com.grupc.userms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(path = "/add")
    public Result createUser (@Valid @RequestBody User user){
        User createdUser = userService.addUser(user);
        return  new DataResult<>(createdUser, true, "User Created");
    }

    @PutMapping(path = "/update")
    public Result updateUser (@Valid @RequestBody User user){
        User updatedUser = userService.updateUser(user);
        return new DataResult<>(updatedUser, true, "User Updated");

    }

    @PutMapping(path = "/update/name")
    public Result updateUserName (@Valid @RequestBody User user){
        User updatedUser = userService.updateUserName(user);
        return new DataResult<>(updatedUser, true, "User Updated");
    }

    @DeleteMapping(path = "/delete") // /delete/{id} deleteUserById
    public Result deleteUser (@RequestBody User user){
        userService.deleteUser(user);
        return new Result("User Deleted", true);
    }
}
