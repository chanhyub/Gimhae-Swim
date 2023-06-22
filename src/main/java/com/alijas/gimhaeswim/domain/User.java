package com.alijas.gimhaeswim.domain;

import com.alijas.gimhaeswim.enums.Sex;
import com.alijas.gimhaeswim.util.BooleanToYNConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSeq;

    @Column(name = "user_name", nullable = false)
    private String userName = "";

    @Column(name = "password", nullable = false)
    private String password = "";

    @Column(name = "birthday", nullable = false)
    private String birthday = "";

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber = "";

    @Column(name = "email", nullable = false)
    private String email = "";

    @Column(name = "organization")
    private String organization = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "is_done", nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isDone;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "team_seq")
    private Team team;

}
