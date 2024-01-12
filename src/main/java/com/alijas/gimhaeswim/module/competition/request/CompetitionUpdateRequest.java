package com.alijas.gimhaeswim.module.competition.request;

import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CompetitionUpdateRequest(
        @NotBlank(message = "대회명을 입력해주세요.")
        String competitionName,
        @NotBlank(message = "대회 날짜를 입력해주세요.")
        String competitionDate,
        @NotBlank(message = "대회 장소를 입력해주세요.")
        String competitionPlace,
        @Valid
        List<CompetitionEventUpdateRequest> competitionEventList,
        @NotBlank(message = "대회 신청 시작일을 입력해주세요.")
        String competitionApplyStartDate,
        @NotBlank(message = "대회 신청 종료일을 입력해주세요.")
        String competitionApplyEndDate,
        @NotBlank(message = "대회 내용을 입력해주세요.")
        String competitionContent,
        @NotNull(message = "대회 참가비를 입력해주세요.")
        Integer competitionFee,
        @NotNull(message = "대회 학생 참가비를 입력해주세요.")
        Integer competitionStudentFee,
        @NotBlank(message = "대회 계좌번호를 입력해주세요.")
        String competitionAccount,
        @NotNull(message = "대회 정보가 존재하지 않습니다.")
        Long competitionId,
        String deleteCompetitionEventIds
) {

        public Competition toCompetition() {
                return new Competition(
                        competitionId,
                        competitionName,
                        DateTimeConverter.StringToLocalDateTime(competitionDate),
                        competitionPlace,
                        DateTimeConverter.StringToLocalDateTime(competitionApplyStartDate),
                        DateTimeConverter.StringToLocalDateTime(competitionApplyEndDate),
                        competitionContent,
                        competitionFee,
                        competitionStudentFee,
                        competitionAccount,
                        CompetitionStatus.ACTIVE
                );
        }
}
