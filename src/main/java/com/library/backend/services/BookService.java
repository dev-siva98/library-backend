package com.library.backend.services;

import java.util.List;

import com.library.backend.model.Book;
import com.library.backend.model.Order;


public interface BookService {

    public Book addBook(Book bookDetails);

    public List<Book> getAllBooks();

    public Book getOneBook(String bookId);

    public Book updateBook(String bookId, Book bookDetails);

    public Boolean deleteBook(String bookId);

    public Order checkoutOrder(Order orderDetails);

}
