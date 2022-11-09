package com.library.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.backend.model.BookModel;
import com.library.backend.services.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addbook")
    public BookModel addBook(@RequestBody BookModel bookData) {
        return bookService.addBook(bookData);
    }

    @GetMapping("/get/all")
    public List<BookModel> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public BookModel getOneBook(@PathVariable("id") String bookId) {
        return bookService.getOneBook(bookId);
    }

    @PutMapping("/update/{id}")
    public BookModel updateBook(@PathVariable("id") String bookId, @RequestBody BookModel bookDetails) {
        return bookService.updateBook(bookId, bookDetails);
    }
}
