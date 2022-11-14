package com.library.backend.services;

import java.util.List;

import com.library.backend.model.Book;


public interface BookService {

    public Book addBook(Book bookDetails);

    public List<Book> getAllBooks();

    public Book getOneBook(String bookId);

    public Book updateBook(String bookId, Book bookDetails);

    public Boolean deleteBook(String bookId);

}
