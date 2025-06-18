package com.sklep.service;

import com.sklep.dao.UserDAO;
import com.sklep.model.User;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class UserService {

    @EJB
    private UserDAO userDAO;

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) { // Zmień na hash check
            return user;
        }
        return null;
    }
}
