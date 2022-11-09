package com.library.backend.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.BookModel;
import com.library.backend.model.OrderModel;
import com.library.backend.model.PreviousIdModel;
import com.library.backend.repository.BookRepository;
import com.library.backend.repository.OrderRepository;
import com.library.backend.repository.PreviousIdRepository;

@Component
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PreviousIdRepository previousIdRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public BookModel addBook(BookModel bookDetails) {

        if (bookRepository.existsByIsbnNo(bookDetails.getIsbnNo()))
            return null;

        PreviousIdModel previousIdModel = previousIdRepository.findByType("book");

        Integer previousBookId = previousIdModel.getPreviousId();

        if (previousBookId < 9)
            bookDetails.set_id("BN00" + ++previousBookId);
        else
            bookDetails.set_id("BN0" + ++previousBookId);

        previousIdModel.setPreviousId(previousBookId);
        previousIdRepository.save(previousIdModel);

        // initially set copiesAvailable same as totalCopies
        bookDetails.setCopiesAvailable(bookDetails.getTotalCopies());

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

    @Override
    public BookModel updateBook(String bookId, BookModel bookDetails) {

        try {
            Optional<BookModel> optionalBook = bookRepository.findById(bookId);
            BookModel book = optionalBook.get();
            if (book == null)
                return null;

            bookDetails.set_id(bookId);
            return bookRepository.save(bookDetails);

        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Boolean deleteBook(String bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Override
    public OrderModel checkoutOrder(OrderModel orderDetails) {

        String userId = orderDetails.getUserId();
        String bookId = orderDetails.getBookId();

        List<OrderModel> orderModelList = orderRepository.findByUserId(userId);

        // a user can only checkout maximum of two books at a time
        if (orderModelList.size() > 1)
            return null;

        orderDetails.setCreatedAt(new Date());

        orderModelList.add(orderDetails);
        orderRepository.saveAll(orderModelList); // saveAll saves iterable OrderModel

        PreviousIdModel previousIdModel = previousIdRepository.findByType("book");

        Integer previousOrderId = previousIdModel.getPreviousId();

        if (previousOrderId < 9)
            orderDetails.set_id("OD00" + ++previousOrderId);
        else
            orderDetails.set_id("OD0" + ++previousOrderId);

        previousIdModel.setPreviousId(previousOrderId);
        previousIdRepository.save(previousIdModel);

        // update availableCopies in book
        Optional<BookModel> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) // return null if book not present
            return null;

        BookModel book = optionalBook.get();

        Integer copiesAvailable = book.getCopiesAvailable();
        book.setCopiesAvailable(--copiesAvailable);

        bookRepository.save(book);

        return orderDetails;
    }

}
