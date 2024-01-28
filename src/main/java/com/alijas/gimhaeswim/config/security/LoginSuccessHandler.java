package com.alijas.gimhaeswim.config.security;

import com.alijas.gimhaeswim.module.common.enums.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        RoleType role = principal.getUser().getRole();
        String url = switch (role.toString()) {
            case "ADMIN" -> "/admin";
            default -> "/";
        };
        response.sendRedirect(url);
    }
}
