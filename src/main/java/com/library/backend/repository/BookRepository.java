package com.library.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.library.backend.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByIsbnNumber(String isbnNo);

    Book save(Book bookDetails);

    List<Book> findAll();

    Optional<Book> findById(String bookId);

    boolean existsById(String bookId);

    void deleteById(String bookId);
}