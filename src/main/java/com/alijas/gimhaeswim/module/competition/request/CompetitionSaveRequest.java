package com.alijas.gimhaeswim.module.competition.request;

public record CompetitionSaveRequest(
        String competitionName,
        String competitionDate,
        String competitionPlace,
        String competitionApplyStartDate,
        String competitionApplyEndDate,
        String competitionContent,
        Integer competitionFee,
        Integer competitionStudentFee,
        String competitionAccount
) {
}
