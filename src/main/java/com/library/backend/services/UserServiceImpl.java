package com.library.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.Book;
import com.library.backend.model.Order;
import com.library.backend.model.PreviousId;
import com.library.backend.model.User;
import com.library.backend.repository.BookRepository;
import com.library.backend.repository.PreviousIdRepository;
import com.library.backend.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreviousIdRepository previousIdRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Order order;

    @Override
    public User createUser(User userDetails) {

        if (userRepository.existsByEmail(userDetails.getEmail())) {
            return null;
        }

        // get document contains previous id
        PreviousId previousIdModel = previousIdRepository.findByType("user");

        Integer previousUserId = previousIdModel.getPreviousId();

        // increment previous id and set as _id in UserModel
        if (previousUserId < 9) {
            userDetails.setId("UN00" + ++previousUserId);
        } else {
            userDetails.setId("UN0" + ++previousUserId);
        }

        // save incremented previousId in PreviousUserModel
        previousIdModel.setPreviousId(previousUserId);
        previousIdRepository.save(previousIdModel);

        userDetails.setCreatedAt(new Date());
        userDetails.setUserRole("USER");
        return userRepository.save(userDetails);
    }

    @Override
    public User userLogin(User userDetails) {
        User user = userRepository.findByEmailAndPassword(userDetails.getEmail(), userDetails.getPassword());
        if (user == null)
            return null;
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getOneUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent())
            return null;

        return optionalUser.get();
    }

    @Override
    public User checkoutBook(String userId, String bookId) {

        User userFromDb = userRepository.findById(userId).get();

        List<Order> orderedBooks = userFromDb.getOrderedBooks();

        order.setCreatedAt(new Date());
        order.setBookId(bookId);

        // max count is 2
        if (orderedBooks.size() > 1) {
            return null;
        }

        orderedBooks.add(order);

        userFromDb.setOrderedBooks(orderedBooks);

        // update availableCopies in book
        Book bookFromDb = bookRepository.findById(bookId).get();

        Integer copiesAvailableForCheckout = bookFromDb.getCopiesAvailableForCheckout();
        bookFromDb.setCopiesAvailableForCheckout(--copiesAvailableForCheckout);

        bookRepository.save(bookFromDb);

        return userRepository.save(userFromDb);
    }

    @Override
    public User checkinBook(String userId, String bookId) {
        User userFromDb = userRepository.findById(userId).get();

        List<Order> orderedBooks = userFromDb.getOrderedBooks();

        // lamda function to remove object from array with condition
        orderedBooks.removeIf(book -> book.getBookId().equals(bookId));

        Book bookFromdb = bookRepository.findById(bookId).get();

        Integer copiesAvailableForCheckout = bookFromdb.getCopiesAvailableForCheckout();
        bookFromdb.setCopiesAvailableForCheckout(++copiesAvailableForCheckout);

        bookRepository.save(bookFromdb);

        return userRepository.save(userFromDb);
    }

}
