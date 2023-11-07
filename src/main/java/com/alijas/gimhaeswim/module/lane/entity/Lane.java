package com.alijas.gimhaeswim.module.lane.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.section.entity.Section;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "LANES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lane extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("레인 차수")
    private Integer laneNumber;

    @Comment("레인 참가 경기인")
    @ManyToOne
    private User user;

    @Comment("레인 참가 팀")
    @ManyToOne
    private Team team;

    @Comment("레인 담당 심판")
    @ManyToOne
    private Referee referee;

    @Comment("소속 조")
    @ManyToOne
    private Section section;

    @Comment("레인 기록")
    private String record;
}
