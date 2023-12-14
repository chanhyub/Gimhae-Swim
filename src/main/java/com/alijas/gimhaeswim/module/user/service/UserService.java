package com.alijas.gimhaeswim.module.user.service;

import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import com.alijas.gimhaeswim.module.user.request.UserSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
