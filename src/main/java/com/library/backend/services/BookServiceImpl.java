package com.library.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.BookModel;
import com.library.backend.repository.BookRepository;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookModel addBook(BookModel bookDetails) {

        // if(bookRepository.existsByIsbnNo(bookDetails.))
        
        return bookRepository.save(bookDetails);
    }
    
}
