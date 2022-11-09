package com.library.backend.services;

import java.util.List;

import com.library.backend.model.UserModel;

public interface UserService {

    public UserModel createUser(UserModel userDetails);

    public UserModel userLogin(UserModel userDetails);

    public List<UserModel> getAllUsers();

    public UserModel getOneUser(String userId);

}
