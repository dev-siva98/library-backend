package com.library.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

}
