package com.library.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User save(User userDetails);

    List<User> findAll();

    Optional<User> findById(String userId);

}
