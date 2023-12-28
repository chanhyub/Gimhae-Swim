package com.alijas.gimhaeswim.module.team.request;

import jakarta.validation.constraints.NotNull;

public record TeamMemberSaveRequest(
        Long teamId,
        Long userId
) {
}
