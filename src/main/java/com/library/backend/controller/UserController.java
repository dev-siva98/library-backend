package com.library.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.backend.model.UserModel;
import com.library.backend.services.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public UserModel signup(@RequestBody UserModel userData ) {
        return userServiceImpl.createUser(userData);
    }

    @PostMapping("/login")
    public UserModel login(@RequestBody UserModel userData) {
        return userServiceImpl.userLogin(userData);
    }

    // @GetMapping
    // public void get() {

    // }
}
