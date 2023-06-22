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
@Table(name = "record")
public class Record {
    @Id
    @Column(name = "record_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordSeq;

    @ManyToOne
    @JoinColumn(name = "competition_event_seq")
    private CompetitionEvent competitionEvent;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "referee_seq")
    private Referee referee;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lane_seq")
    private Lane lane;

    @Column(name = "record", nullable = false)
    private String record = "";

}
