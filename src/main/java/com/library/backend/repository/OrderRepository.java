package com.library.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.OrderModel;

public interface OrderRepository extends MongoRepository<OrderModel, String> {

    List<OrderModel> findByUserId(String userId);

}
