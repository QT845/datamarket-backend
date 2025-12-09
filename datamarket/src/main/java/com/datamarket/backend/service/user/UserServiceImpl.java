package com.datamarket.backend.service.user;

import com.datamarket.backend.entity.User;
import com.datamarket.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) { return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserById(Long id) { return userRepository.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) { return userRepository.existsByUsername(username);
    }

    @Override
    public User saveUser(User user) { return userRepository.save(user);
    }
}
