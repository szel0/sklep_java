package com.sklep.service;

import com.sklep.model.User;

import java.util.Collection;

public interface UserService {
    User register(User user) throws IllegalArgumentException;
    User login(String username, String password) throws IllegalArgumentException;
    Collection<User> getAllUsers();

}
