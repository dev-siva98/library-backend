package com.library.backend.services;

import java.util.Date;

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
    public UserModel createUser(UserModel userData) {

        if (userRepository.existsByEmail(userData.getEmail())) {
            return null;
        }

        // get document contains previous id's
        PreviousIdModel previousIds = previousIdRepository.findAll().get(0);

        Integer previousUserId = previousIds.getUserId();

        //increment previous id and set as _id in UserModel
        if (previousUserId < 9) {
            userData.set_id("UN00" + ++previousUserId);
        } else {
            userData.set_id("UN0" + ++previousUserId);
        }

        //save incremented previous id in PreviousUserModel
        previousIds.setUserId(previousUserId);
        previousIdRepository.save(previousIds);

        userData.setCreatedAt(new Date());
        return userRepository.save(userData);
    }

    @Override
    public UserModel userLogin(UserModel userData) {
        UserModel user = userRepository.findByEmailAndPassword(userData.getEmail(), userData.getPassword());
        if (user == null)
            return null;
        return user;
    }

}
