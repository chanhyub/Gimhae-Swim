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
@Table(name = "lane_section")
public class LaneSection {
    @Id
    @Column(name = "lane_section_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer laneSectionSeq;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "is_done", nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isDone = false;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "competition_event_seq")
    private CompetitionEvent competitionEvent;

    @JsonManagedReference
    @OneToMany(mappedBy = "laneSection", orphanRemoval = true)
    List<Lane> laneList = new ArrayList<>();
}
