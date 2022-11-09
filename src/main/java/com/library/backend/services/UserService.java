package com.library.backend.services;

import java.util.List;

import com.library.backend.model.UserModel;

public interface UserService {

    public UserModel createUser(UserModel userData);

    public UserModel userLogin(UserModel userData);

    public List<UserModel> getAllUsers();
}
