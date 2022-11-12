package com.library.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.backend.model.User;
import com.library.backend.services.UserServiceImpl;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public User signup(@RequestBody User userDetails) {
        return userServiceImpl.createUser(userDetails);
    }

    @PostMapping("/login")
    public User login(@RequestBody User userDetails) {
        return userServiceImpl.userLogin(userDetails);
    }

    @GetMapping("/get/all")
    public List<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public User getOneUser(@PathVariable("id") String userId) {
        return userServiceImpl.getOneUser(userId);
    }
}
