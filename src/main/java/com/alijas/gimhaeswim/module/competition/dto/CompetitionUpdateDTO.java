package com.alijas.gimhaeswim.module.competition.dto;

import com.alijas.gimhaeswim.module.competition.entity.Competition;

public record CompetitionUpdateDTO(
        Long id,
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
