package com.g4AppDev.FoundIT.service;


import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @SuppressWarnings("finally")
    public UserEntity updateUser(Long id, UserEntity userDetails) {
    	UserEntity user = new UserEntity();
        try {
            user = userRepository.findById(id).get();
            user.setSchoolEmail(userDetails.getSchoolEmail());
            user.setSchoolId(userDetails.getSchoolId());
            user.setPassword(userDetails.getPassword());
            user.setBio(userDetails.getBio());
            user.setCurrentPoints(userDetails.getCurrentPoints());
        } catch (NoSuchElementException nex) {
            throw new RuntimeException("User " + id + " not found");
        } finally {
            return userRepository.save(user);
        }
    }

    public String deleteUser(Long id) {
        String msg;

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            msg = "User record successfully deleted";
        } else {
            msg = id + " NOT found!";
        }
        return msg;
    }
    
    public List<UserEntity> getLatestUsers(int count) {
        return userRepository.findAll().stream()
            .sorted((u1, u2) -> Long.compare(u2.getUserID(), u1.getUserID()))
            .limit(count) 
            .collect(Collectors.toList());
    }
    
    public long getUserCount() {
        return userRepository.count();
    }
}
