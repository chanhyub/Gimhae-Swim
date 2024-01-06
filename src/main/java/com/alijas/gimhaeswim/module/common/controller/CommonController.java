package com.alijas.gimhaeswim.module.common.controller;

import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.common.request.LoginRequest;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    private final UserService userService;

    public CommonController(UserService userService) {
        this.userService = userService;
    }


//    @PostMapping("/login")
}
