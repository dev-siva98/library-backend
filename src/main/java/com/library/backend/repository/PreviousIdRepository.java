package com.library.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.PreviousIdModel;

public interface PreviousIdRepository extends MongoRepository<PreviousIdModel, String> {

    PreviousIdModel findByType(String type);

}
