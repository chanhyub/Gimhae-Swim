package com.alijas.gimhaeswim.domain;

import com.alijas.gimhaeswim.util.BooleanToYNConverter;
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
@Table(name = "application_detail")
public class ApplicationDetail {
    @Id
    @Column(name = "application_detail_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationDetailSeq;

    @Column(name = "is_deposit", nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isDeposit;

    @ManyToOne
    @JoinColumn(name = "competition_seq")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_seq")
    private Team team;

    @JsonManagedReference
    @OneToMany(mappedBy = "applicationDetail", orphanRemoval = true)
    List<CompetitionEventApplicationDetail> competitionEventApplicationDetailList = new ArrayList<>();
}
