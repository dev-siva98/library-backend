package com.library.backend.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.backend.model.PreviousIdModel;
import com.library.backend.model.UserModel;
import com.library.backend.repository.PreviousIdRepository;
import com.library.backend.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreviousIdRepository previousIdRepository;

    @Override
    public UserModel createUser(UserModel userDetails) {

        if (userRepository.existsByEmail(userDetails.getEmail())) {
            return null;
        }

        // get document contain previous id's
        PreviousIdModel previousIds = previousIdRepository.findAll().get(0);

        Integer previousUserId = previousIds.getUserId();

        // increment previous id and set as _id in UserModel
        if (previousUserId < 9) {
            userDetails.set_id("UN00" + ++previousUserId);
        } else {
            userDetails.set_id("UN0" + ++previousUserId);
        }

        // save incremented previousId in PreviousUserModel
        previousIds.setUserId(previousUserId);
        previousIdRepository.save(previousIds);

        userDetails.setCreatedAt(new Date());
        return userRepository.save(userDetails);
    }

    @Override
    public UserModel userLogin(UserModel userDetails) {
        UserModel user = userRepository.findByEmailAndPassword(userDetails.getEmail(), userDetails.getPassword());
        if (user == null)
            return null;
        return user;
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel getOneUser(String userId) {
        try {
            Optional<UserModel> userOptional = userRepository.findById(userId);
            return userOptional.get();
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
