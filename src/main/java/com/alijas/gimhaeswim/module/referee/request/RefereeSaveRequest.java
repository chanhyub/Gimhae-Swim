package com.alijas.gimhaeswim.module.referee.request;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.enums.Gender;
import com.alijas.gimhaeswim.module.common.enums.RoleType;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;

public record RefereeSaveRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        String username,
        @NotBlank(message = "성명을 입력해주세요.")
        String fullName,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String confirmPassword,
        @NotBlank(message = "주민번호 앞자리를 입력해주세요.")
        String birthday,
        @NotBlank(message = "전화번호을 입력해주세요.")
        String phoneNumber,
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @NotBlank(message = "성별을 입력해주세요.")
        String gender
) {
    public User toEntity() {
        return new User(
                null,
                username,
                fullName,
                password,
                birthday,
                phoneNumber,
                email,
                Gender.valueOf(gender),
                true,
                UserStatus.ACTIVE,
                ApplyStatus.WAITING,
                RoleType.REFEREE
        );
    }
}
