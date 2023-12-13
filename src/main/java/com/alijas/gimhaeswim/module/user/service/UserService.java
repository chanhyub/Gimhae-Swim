package com.alijas.gimhaeswim.module.user.service;

import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }
}
