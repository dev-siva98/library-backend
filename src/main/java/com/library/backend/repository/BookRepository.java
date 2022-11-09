package com.library.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.BookModel;

public interface BookRepository extends MongoRepository<BookModel, String> {

    boolean existsByIsbnNo(String isbnNo);

}
