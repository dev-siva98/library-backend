package com.library.backend.services;

import java.util.List;

import com.library.backend.model.User;

public interface UserService {

    public User createUser(User userDetails);

    public User userLogin(User userDetails);

    public List<User> getAllUsers();

    public User getOneUser(String userId);

    public User checkoutBook(String userId, String bookId);

}
