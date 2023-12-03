package com.alijas.gimhaeswim.module.competition.dto;

public record CompetitionEventListApplyDTO(
        Long id,
        String eventType,
        String department,
        String event,
        String meter
) {
}
