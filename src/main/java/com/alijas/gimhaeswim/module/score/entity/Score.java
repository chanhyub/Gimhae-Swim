package com.alijas.gimhaeswim.module.score.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCORES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("기록 대회 종목")
    @ManyToOne
    private CompetitionEvent competitionEvent;

    @Comment("대회 참가자")
    @ManyToOne
    private User user;

    @Comment("점수")
    private Integer record;
}
