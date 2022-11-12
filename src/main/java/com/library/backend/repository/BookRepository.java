package com.library.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.library.backend.model.BookModel;

public interface BookRepository extends MongoRepository<BookModel, String> {

    Boolean existsByIsbnNo(String isbnNo);

    BookModel save(BookModel bookDetails);

    List<BookModel> findAll();

    Optional<BookModel> findById(String bookId);

    boolean existsById(String bookId);

    void deleteById(String bookId);
}