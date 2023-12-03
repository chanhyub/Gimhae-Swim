package com.alijas.gimhaeswim.module.competition.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionApplyDTO;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionDetailDTO;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMPETITIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Competition extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("대회 이름")
    private String competitionName;

    @Comment("대회 일시")
    private LocalDateTime competitionDate;

    @Comment("대회 장소")
    private String competitionPlace;

    @Comment("대회 신청 시작일")
    private LocalDateTime competitionApplyStartDate;

    @Comment("대회 신청 마감일")
    private LocalDateTime competitionApplyEndDate;

    @Comment("대회 내용")
    @Column(columnDefinition = "LONGTEXT")
    private String competitionContent;

    @Comment("대회 성인 참가비")
    private Integer competitionFee;

    @Comment("대회 학생(만 18세) 참가비")
    private Integer competitionStudentFee;

    @Comment("대회 참가비 입금 계좌 정보(은행, 계좌번호, 예금주)")
    private String competitionAccount;

    @Comment("대회 상태")
    @Enumerated(EnumType.STRING)
    private CompetitionStatus status;

    public CompetitionListDTO toCompetitionListDTO() {
        return new CompetitionListDTO(
                this.id,
                this.competitionName,
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionDate),
                this.competitionPlace,
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyStartDate),
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyEndDate)
        );
    }

    public CompetitionDetailDTO toCompetitionDetailDTO() {
        return new CompetitionDetailDTO(
                this.id,
                this.competitionName,
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyStartDate),
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyEndDate),
                this.competitionContent
        );
    }

    public CompetitionApplyDTO toCompetitionApplyDTO() {
        return new CompetitionApplyDTO(
                this.id,
                this.competitionName,
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyStartDate),
                DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.competitionApplyEndDate),
                this.competitionFee,
                this.competitionStudentFee,
                this.competitionAccount
        );
    }
}
