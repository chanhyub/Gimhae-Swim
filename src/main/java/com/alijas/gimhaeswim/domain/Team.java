package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "team_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamSeq;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @JsonManagedReference
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    List<User> userList = new ArrayList<>();
}
