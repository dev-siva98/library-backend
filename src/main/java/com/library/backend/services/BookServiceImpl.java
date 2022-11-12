package com.library.backend.services;

import java.util.Date;
import java.util.List;
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
            bookDetails.setId("BN00" + ++previousBookId);
        else
            bookDetails.setId("BN0" + ++previousBookId);

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
        Optional<BookModel> optionalBook = bookRepository.findById(bookId);

        if (!optionalBook.isPresent())
            return null;

        return optionalBook.get();
    }

    @Override
    public BookModel updateBook(String bookId, BookModel bookDetails) {

        Optional<BookModel> optionalBook = bookRepository.findById(bookId);

        if (!optionalBook.isPresent())
            return null;

        BookModel bookFromDb = optionalBook.get();

        // best practice to update each field individually
        bookFromDb.setTitle(bookDetails.getTitle());
        bookFromDb.setAuthor(bookDetails.getAuthor());
        bookFromDb.setGenre(bookDetails.getGenre());
        bookFromDb.setIsbnNo(bookDetails.getIsbnNo());
        bookFromDb.setImg(bookDetails.getImg());

        // update copiesAvailable field according to update in the total copies of the
        // book
        Integer totalCopiesInDb = bookFromDb.getTotalCopies();
        Integer totalCopiesFromUser = bookDetails.getTotalCopies();
        Integer copiesAvailable = bookFromDb.getCopiesAvailable();

        // it will edit copiesAvailable according to totalCopies even if it is lesser or
        // greater
        if (totalCopiesInDb != totalCopiesFromUser) {
            bookFromDb.setCopiesAvailable(copiesAvailable + (totalCopiesFromUser - totalCopiesInDb));
        }

        // setting total after the copiesAvailable
        bookFromDb.setTotalCopies(bookDetails.getTotalCopies());

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
            orderDetails.setId("OD00" + ++previousOrderId);
        else
            orderDetails.setId("OD0" + ++previousOrderId);

        previousIdModel.setPreviousId(previousOrderId);
        previousIdRepository.save(previousIdModel);

        // update availableCopies in book
        Optional<BookModel> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) // return null if book not present
            return null;

        BookModel bookFromDb = optionalBook.get();

        Integer copiesAvailable = bookFromDb.getCopiesAvailable();
        bookFromDb.setCopiesAvailable(--copiesAvailable);

        bookRepository.save(bookFromDb);

        return orderDetails;
    }

}
