package com.alijas.gimhaeswim.module.team.dto;

public record TeamMemberDTO(
    Long id,
    Long teamId,
    String teamName,
    String position
) {
}
