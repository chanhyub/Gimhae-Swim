package com.alijas.gimhaeswim.module.competition.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionEventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/*
*    대회 종목 테이블
*/

@Entity
@Table(name = "COMPETITION_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionEvent extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("종목 유형")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Comment("종목 부별") /* ex) 13세이하(여자), 13세이하(남자) 등등 */
    @ManyToOne
    private Department department;

    @Comment("종목 종류")
    @ManyToOne
    private Event event;

    @Comment("종목 미터")
    @ManyToOne
    private Meter meter;

    @Comment("종목 정원")
    private Integer eventCapacity;

    @Comment("1등 스코어")
    private Integer firstScore;

    @Comment("2등 스코어")
    private Integer secondScore;

    @Comment("3등 스코어")
    private Integer thirdScore;

    @Comment("4등 스코어")
    private Integer fourthScore;

    @Comment("5등 스코어")
    private Integer fifthScore;

    @Comment("6등 스코어")
    private Integer sixthScore;

    @Comment("7등 스코어")
    private Integer seventhScore;

    @Comment("8등 스코어")
    private Integer eighthScore;

    @Comment("대회")
    @ManyToOne
    private Competition competition;

    public CompetitionEventListApplyDTO toCompetitionEventListApplyDTO() {
        return new CompetitionEventListApplyDTO(
                this.id,
                this.eventType.name(),
                this.department.getDepartmentGender().name(),
                this.department.getDepartmentName(),
                this.event.getEventName(),
                this.meter.getMeter()
        );
    }
}