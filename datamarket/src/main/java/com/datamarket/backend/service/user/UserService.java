package com.datamarket.backend.service.user;

import com.datamarket.backend.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    boolean existsByEmail(String email);
    User saveUser(User user);
}
