package com.Dou888311.recipes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Dou888311.recipes.Entity.User;
import com.Dou888311.recipes.Services.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void registerUser(@RequestBody @Valid User user) {
        userService.userRegister(user);
    }
}
