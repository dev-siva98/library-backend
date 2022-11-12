package com.library.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.backend.model.Book;
import com.library.backend.model.Order;
import com.library.backend.services.BookServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @PostMapping("/addbook")
    public Book addBook(@RequestBody Book bookData) {
        return bookServiceImpl.addBook(bookData);
    }

    @GetMapping("/get/all")
    public List<Book> getAllBooks() {
        return bookServiceImpl.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public Book getOneBook(@PathVariable("id") String bookId) {
        return bookServiceImpl.getOneBook(bookId);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable("id") String bookId, @RequestBody Book bookDetails) {
        return bookServiceImpl.updateBook(bookId, bookDetails);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteBook(@PathVariable("id") String bookId) {
        return bookServiceImpl.deleteBook(bookId);
    }

    @PostMapping("/checkout")
    public Order checkoutOrder(@RequestBody Order orderDetails) {
        return bookServiceImpl.checkoutOrder(orderDetails);
    }
}
