package com.alijas.gimhaeswim.dto;

import com.alijas.gimhaeswim.domain.Competition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompetitionEventDto {
    private String sex;
    private String division;
    private String eventName;
    private String meter;
    private String personnel;
    private Competition competition;
}
