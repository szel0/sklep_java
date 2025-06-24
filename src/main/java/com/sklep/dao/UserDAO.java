package com.sklep.dao;

import com.sklep.model.User;
import java.util.Optional;
import java.util.*;

public interface UserDAO {
    User findById(Long id);
    Optional<User> findByUsername(String username);
    User add(User user);
    Collection<User> findAll();
}
