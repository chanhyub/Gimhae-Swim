package com.alijas.gimhaeswim.module.competition.dto;

public record CompetitionApplyDTO(
        Long id,
        String competitionName,
        String competitionApplyStartDate,
        String competitionApplyEndDate,
        Integer competitionFee,
        Integer competitionStudentFee,
        String competitionAccount
) {
}
