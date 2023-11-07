package com.alijas.gimhaeswim.module.user.entity;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.enums.RoleType;
import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.user.enums.Gender;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사용자 아이디")
    @Column(unique = true)
    private String username;

    @Comment("사용자 비밀번호")
    private String password;

    @Comment("사용자 생년월일")
    private String birthday;

    @Comment("사용자 전화번호")
    private String phoneNumber;

    @Comment("사용자 이메일")
    private String email;

    @Comment("사용자 성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("이용약관 동의 여부")
    private boolean isAgree;
/* TODO : 상태값 2개 ? */
    @Comment("사용자 상태")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Comment("사용자 승인 상태")
    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;
/* */
    @Comment("사용자 권한")
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
