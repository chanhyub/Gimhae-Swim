package com.alijas.gimhaeswim.module.applycompetition.entity;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "APPLY_COMPETITIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyCompetition extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("신청 대회 종목")
    @ManyToOne
    private CompetitionEvent competitionEvent;

    @Comment("신청 대회 참가자")
    @ManyToOne
    private User user;

    @Comment("신청 대회 참가팀")
    @ManyToOne
    private Team team;

    @Comment("승인 상태")
    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;
}
