package com.library.backend.services;

import com.library.backend.model.UserModel;

public interface UserService {

    public UserModel createUser(UserModel userData);

    public UserModel userLogin(UserModel userData);
}
