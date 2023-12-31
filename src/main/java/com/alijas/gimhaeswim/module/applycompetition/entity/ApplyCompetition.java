package com.alijas.gimhaeswim.module.applycompetition.entity;

import com.alijas.gimhaeswim.module.applycompetition.dto.ApplyCompetitionListDTO;
import com.alijas.gimhaeswim.module.applycompetition.enums.DepositStatus;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "APPLY_COMPETITIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    private Long id;

    @Comment("신청 대회")
    @ManyToOne
    private Competition competition;

    @Comment("신청 대회 참가자")
    @ManyToOne
    private User user;

    @Comment("신청 대회 참가팀")
    @ManyToOne
    private Team team;

    @Comment("입금 여부")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NOT_DEPOSITED'")
    private DepositStatus depositStatus;

    @Comment("승인 상태")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAITING'")
    private ApplyStatus applyStatus;

    public ApplyCompetitionListDTO toApplyCompetitionListDTO() {
        return new ApplyCompetitionListDTO(
                this.id,
                this.getCompetition().getId().toString(),
                this.competition.getCompetitionName(),
                this.depositStatus.name(),
                null
        );
    }
}
