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
        try{
            User createdUser = userService.addUser(user);
            return  new DataResult<>(createdUser, true, "User Created");
        }catch(UserException e){
            return new Result(e.getMessage(), false );
        }

    }

    @PutMapping(path = "/update")
    public Result updateUser (@Valid @RequestBody User user){
        try{
            User updatedUser = userService.updateUser(user);
            return new DataResult<>(updatedUser, true, "User Updated");
        }
        catch (UserException e){
            return new Result(e.getMessage(), false);
        }

    }

    @DeleteMapping(path = "/delete/user")
    public Result deleteUser (@RequestBody User user){
        try{
            userService.deleteUser(user);
            return new Result("User Deleted", true);
        }
        catch (UserException e){
            return new Result(e.getMessage(), false);
        }
    }
}
