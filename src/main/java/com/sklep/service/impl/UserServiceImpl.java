package com.sklep.service.impl;

import com.sklep.service.UserService;
import com.sklep.dao.UserDAO;
import com.sklep.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserDAO userDao;

    @Override
    public User register(User user) throws IllegalArgumentException {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        return userDao.add(user);
    }

    @Override
    public User login(String username, String password) throws IllegalArgumentException {
        var optUser = userDao.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        var user = optUser.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDao.findAll();
    }
}
