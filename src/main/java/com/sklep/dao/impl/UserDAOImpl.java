package com.sklep.dao.impl;

import com.sklep.dao.UserDAO;
import com.sklep.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Collection;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 1;

    public UserDAOImpl() {
        User admin = new User();
        User user = new User();

        admin.setUsername("admin");
        user.setUsername("user");

        admin.setPassword("admin");
        user.setPassword("user");

        admin.setRole(User.UserRole.ADMIN);
        user.setRole(User.UserRole.USER);

        admin.setId(currentId++);
        user.setId(currentId++);

        users.put(admin.getId(), admin);
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public User add(User user) {
        user.setId(currentId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long id) {
        return users.get(id);
    }
}
