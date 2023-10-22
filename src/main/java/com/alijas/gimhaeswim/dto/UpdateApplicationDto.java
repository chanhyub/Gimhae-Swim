package com.alijas.gimhaeswim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateApplicationDto {
    private String userName;
    private String password;
    private String setCompetitionSeq;
}
