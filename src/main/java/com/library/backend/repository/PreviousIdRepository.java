package com.library.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.PreviousId;

import org.springframework.stereotype.Repository;

@Repository
public interface PreviousIdRepository extends MongoRepository<PreviousId, String> {

    PreviousId findByType(String type);

    PreviousId save(PreviousId previousIdModel);

}
