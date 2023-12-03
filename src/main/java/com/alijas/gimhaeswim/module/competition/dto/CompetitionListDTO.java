package com.alijas.gimhaeswim.module.competition.dto;

public record CompetitionListDTO(
    Long id,
    String competitionName,
    String competitionDate,
    String competitionPlace,
    String competitionApplyStartDate,
    String competitionApplyEndDate

) {
}
