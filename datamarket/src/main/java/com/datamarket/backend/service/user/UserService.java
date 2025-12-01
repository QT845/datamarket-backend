package com.datamarket.backend.service.user;

import com.datamarket.backend.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User saveUser(User user);
}
