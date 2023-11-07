package com.alijas.gimhaeswim.config.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SecurityService {

    private final UserRepository userRepository;
}
