package com.alijas.gimhaeswim.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "referee")
public class Referee {
    @Id
    @Column(name = "referee_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refereeSeq;

    @Column(name = "referee_name", nullable = false)
    private String refereeName;

    @Column(name = "password", nullable = false)
    private String password;
}
