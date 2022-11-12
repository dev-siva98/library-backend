package com.library.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {

    Boolean existsByEmail(String email);

    UserModel findByEmailAndPassword(String email, String password);

    UserModel save(UserModel userDetails);

    List<UserModel> findAll();

    Optional<UserModel> findById(String userId);

}
