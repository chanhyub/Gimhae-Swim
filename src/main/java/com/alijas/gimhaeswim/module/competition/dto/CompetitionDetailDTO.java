package com.alijas.gimhaeswim.module.competition.dto;

public record CompetitionDetailDTO(
        Long id,
        String competitionName,
        String competitionApplyStartDate,
        String competitionApplyEndDate,
        String competitionContent
) {
}
