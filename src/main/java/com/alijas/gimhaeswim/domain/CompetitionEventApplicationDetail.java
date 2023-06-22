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
@Table(name = "competitionEvent_applicationDetail")
public class CompetitionEventApplicationDetail {
    @Id
    @Column(name = "competitionEvent_applicationDetail_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer competitionEvent_applicationDetail_seq;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "competition_event_seq")
    private CompetitionEvent competitionEvent;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "application_detail_seq")
    private ApplicationDetail applicationDetail;
}
