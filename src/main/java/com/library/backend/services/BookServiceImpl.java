package com.library.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.Book;
import com.library.backend.model.PreviousId;
import com.library.backend.repository.BookRepository;
import com.library.backend.repository.PreviousIdRepository;

@Component
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PreviousIdRepository previousIdRepository;

    @Override
    public Book addBook(Book bookDetails) {

        if (bookRepository.existsByIsbnNumber(bookDetails.getIsbnNumber()))
            return null;

        PreviousId previousIdModel = previousIdRepository.findByType("book");

        Integer previousBookId = previousIdModel.getPreviousId();

        if (previousBookId < 9)
            bookDetails.setId("BN00" + ++previousBookId);
        else
            bookDetails.setId("BN0" + ++previousBookId);

        previousIdModel.setPreviousId(previousBookId);
        previousIdRepository.save(previousIdModel);

        // initially set copiesAvailable same as totalCopies
        bookDetails.setCopiesAvailableForCheckout(bookDetails.getTotalNumberOfCopies());

        bookDetails.setCreatedAt(new Date());

        return bookRepository.save(bookDetails);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getOneBook(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (!optionalBook.isPresent())
            return null;

        return optionalBook.get();
    }

    @Override
    public Book updateBook(String bookId, Book bookDetails) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (!optionalBook.isPresent())
            return null;

        Book bookFromDb = optionalBook.get();

        // best practice to update each field individually
        bookFromDb.setTitle(bookDetails.getTitle());
        bookFromDb.setAuthor(bookDetails.getAuthor());
        bookFromDb.setGenre(bookDetails.getGenre());
        bookFromDb.setIsbnNumber(bookDetails.getIsbnNumber());
        bookFromDb.setImageUrl(bookDetails.getImageUrl());

        // update copiesAvailable field according to update in the total copies of the
        // book
        Integer totalNumberOfCopiesInDb = bookFromDb.getTotalNumberOfCopies();
        Integer totalNumberOfCopiesFromUser = bookDetails.getTotalNumberOfCopies();
        Integer copiesAvailableForCheckoutInDb = bookFromDb.getCopiesAvailableForCheckout();

        // edit copiesAvailable according to totalCopies even if it is lesser or greater
        if (totalNumberOfCopiesInDb != totalNumberOfCopiesFromUser) {
            bookFromDb.setCopiesAvailableForCheckout(
                    copiesAvailableForCheckoutInDb + (totalNumberOfCopiesFromUser - totalNumberOfCopiesInDb));
        }

        // setting total after the copiesAvailable updated
        bookFromDb.setTotalNumberOfCopies(bookDetails.getTotalNumberOfCopies());

        return bookRepository.save(bookFromDb);

    }

    @Override
    public Boolean deleteBook(String bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    // @Override
    // public Order checkoutOrder(Order orderDetails) {

    // String userId = orderDetails.getUserId();
    // String bookId = orderDetails.getBookId();

    // List<Order> orderModelList = orderRepository.findByUserId(userId);

    // // a user can only checkout maximum of two books at a time
    // if (orderModelList.size() > 1)
    // return null;

    // orderDetails.setCreatedAt(new Date());

    // orderModelList.add(orderDetails);
    // orderRepository.saveAll(orderModelList); // saveAll saves iterable OrderModel

    // PreviousId previousIdModel = previousIdRepository.findByType("book");

    // Integer previousOrderId = previousIdModel.getPreviousId();

    // if (previousOrderId < 9)
    // orderDetails.setId("OD00" + ++previousOrderId);
    // else
    // orderDetails.setId("OD0" + ++previousOrderId);

    // previousIdModel.setPreviousId(previousOrderId);
    // previousIdRepository.save(previousIdModel);

    // // update availableCopies in book
    // Optional<Book> optionalBook = bookRepository.findById(bookId);
    // if (!optionalBook.isPresent()) // return null if book not present
    // return null;

    // Book bookFromDb = optionalBook.get();

    // Integer copiesAvailable = bookFromDb.getCopiesAvailable();
    // bookFromDb.setCopiesAvailable(--copiesAvailable);

    // bookRepository.save(bookFromDb);

    // return orderDetails;
    // }

}
