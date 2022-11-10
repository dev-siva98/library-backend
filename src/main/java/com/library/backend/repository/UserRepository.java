package com.library.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {

    Boolean existsByEmail(String email);

    UserModel findByEmailAndPassword(String email, String password);

}
