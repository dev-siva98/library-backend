package com.library.backend.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.BookModel;
import com.library.backend.model.PreviousIdModel;
import com.library.backend.repository.BookRepository;
import com.library.backend.repository.PreviousIdRepository;

@Component
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PreviousIdRepository previousIdRepository;

    @Override
    public BookModel addBook(BookModel bookDetails) {

        if (bookRepository.existsByIsbnNo(bookDetails.getIsbnNo()))
            return null;

        PreviousIdModel previousIdModel = previousIdRepository.findByType("book");

        Integer previousBookId = previousIdModel.getPreviousId();

        if (previousBookId < 9) {
            bookDetails.set_id("BN00" + ++previousBookId);
        } else {
            bookDetails.set_id("BN0" + ++previousBookId);
        }

        previousIdModel.setPreviousId(previousBookId);
        previousIdRepository.save(previousIdModel);

        bookDetails.setCreatedAt(new Date());

        return bookRepository.save(bookDetails);
    }

    @Override
    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookModel getOneBook(String bookId) {
        try {
            Optional<BookModel> optionalBook = bookRepository.findById(bookId);
            return optionalBook.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
