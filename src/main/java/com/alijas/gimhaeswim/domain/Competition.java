package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "competition")
public class Competition {
    @Id
    @Column(name = "competition_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer competitionSeq;

    @Column(name = "competition_name", nullable = false)
    private String competitionName;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "competition_period", nullable = false)
    private String competitionPeriod = "";

    @Column(name = "detail")
    private String detail = "";

    @Column(name = "place", nullable = false)
    private String place = "";

    @Column(name = "price", nullable = false)
    private String price = "";

    @JsonManagedReference
    @OneToMany(mappedBy = "competition", orphanRemoval = true)
    List<CompetitionEvent> competitionEventList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "competition", orphanRemoval = true)
    List<ApplicationDetail> applicationDetailList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "competition", orphanRemoval = true)
    List<TeamPoint> teamPointList = new ArrayList<>();
}
