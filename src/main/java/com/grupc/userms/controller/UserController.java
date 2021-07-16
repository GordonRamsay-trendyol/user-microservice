package com.grupc.userms.controller;

import com.grupc.userms.entities.User;
import com.grupc.userms.model.request.CreateUserRequest;
import com.grupc.userms.model.request.UpdateUserRequest;
import com.grupc.userms.services.UserService;
import com.grupc.userms.model.response.DataResult;
import com.grupc.userms.model.response.Result;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public Result createUser (@Valid @RequestBody CreateUserRequest request){
        User createdUser = userService.addUser(request);
        return  new DataResult<>(createdUser, true, "User Created");
    }

    @PutMapping
    public Result updateUser (@Valid @RequestBody UpdateUserRequest request){
        User updatedUser = userService.updateUser(request);
        return new DataResult<>(updatedUser, true, "User Updated");

    }

    @PutMapping(path = "/name")
    public Result updateUserName (@Valid @RequestBody UpdateUserRequest request){
        User updatedUser = userService.updateUserName(request);
        return new DataResult<>(updatedUser, true, "User Updated");
    }

    @GetMapping
    public Result getAllUsers (){
        return new DataResult<>(userService.getAllUsers(),true,"Returned all users");
    }

    @DeleteMapping
    public Result deleteUser (@RequestBody User user){
        userService.deleteUser(user);
        return new Result("User Deleted", true);
    }
}
