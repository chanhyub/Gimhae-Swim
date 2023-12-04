package com.alijas.gimhaeswim.module.competition.dto;

public record CompetitionEventListApplyDTO(
        Long id,
        String eventType,
        String gender,
        String department,
        String event,
        String meter
) {
}
