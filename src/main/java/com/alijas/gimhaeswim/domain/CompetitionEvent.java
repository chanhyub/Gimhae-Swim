package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "competition_event")
public class CompetitionEvent {
    @Id
    @Column(name = "competition_event_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer competitionEventSeq;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "division", nullable = false)
    private String division;

    @Column(name = "event_name", nullable = false)
    private String eventName = "";

    @Column(name = "meter", nullable = false)
    private String meter = "";

    @Column(name = "personnel", nullable = false)
    private String personnel = "";

    @Column(name = "first_point", nullable = false)
    private String firstPoint = "";

    @Column(name = "second_point", nullable = false)
    private String secondPoint = "";

    @Column(name = "third_point", nullable = false)
    private String thirdPoint = "";

    @Column(name = "forth_point", nullable = false)
    private String forthPoint = "";

    @Column(name = "fifth_point", nullable = false)
    private String fifthPoint = "";

    @Column(name = "sixth_point", nullable = false)
    private String sixthPoint = "";

    @Column(name = "seventh_point", nullable = false)
    private String seventhPoint = "";

    @Column(name = "eighth_point", nullable = false)
    private String eighthPoint = "";

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "competition_seq")
    private Competition competition;

    @JsonManagedReference
    @OneToMany(mappedBy = "competitionEvent", orphanRemoval = true)
    List<LaneSection> laneSectionList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "competitionEvent", orphanRemoval = true)
    List<CompetitionEventApplicationDetail> competitionEventApplicationDetailList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "competitionEvent", orphanRemoval = true)
    List<TeamPoint> teamPointList = new ArrayList<>();
}
