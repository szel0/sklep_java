package com.sklep.dao;

import com.sklep.model.User;
import java.util.List;

public interface UserDAO {
    void create(User user);
    User findByUsername(String username);
    List<User> findAll();
}
