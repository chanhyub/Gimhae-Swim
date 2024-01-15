package com.alijas.gimhaeswim.module.referee.dto;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.user.dto.UserDTO;

public record RefereeDTO(
    Long id,
    UserDTO user,
    ApplyStatus status
) {
}
