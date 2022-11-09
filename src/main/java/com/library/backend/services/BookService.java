package com.library.backend.services;

import java.util.List;

import com.library.backend.model.BookModel;


public interface BookService {

    public BookModel addBook(BookModel bookDetails);

    public List<BookModel> getAllBooks();

    public BookModel getOneBook(String bookId);

    public BookModel updateBook(String bookId, BookModel bookDetails);

    public Boolean deleteBook(String bookId);
}
