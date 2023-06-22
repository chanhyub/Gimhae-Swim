package com.alijas.gimhaeswim.domain;

import com.alijas.gimhaeswim.util.BooleanToYNConverter;
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
@Table(name = "lane")
public class Lane {
    @Id
    @Column(name = "lane_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer laneSeq;

    @Column(name = "lane", nullable = false)
    private String lane;

    @Column(name = "is_done", nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_seq")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "referee_seq")
    private Referee referee;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lane_section_seq")
    private LaneSection laneSection;

    @JsonManagedReference
    @OneToMany(mappedBy = "lane", orphanRemoval = true)
    List<Record> recordList = new ArrayList<>();
}
