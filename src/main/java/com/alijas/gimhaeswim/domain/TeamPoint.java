package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team_point")
public class TeamPoint {
    @Id
    @Column(name = "team_point_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamPointSeq;

    @Column(name = "point", nullable = false)
    private String point;

    @ManyToOne
    @JoinColumn(name = "team_seq")
    private Team team;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "competition_event_seq")
    private CompetitionEvent competitionEvent;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "competition_seq")
    private Competition competition;

}
