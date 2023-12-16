package com.alijas.gimhaeswim.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String fullName;

    private String birthday;

    private String phoneNumber;

    private String email;

    private String gender;

    private String role;
}
