package com.alijas.gimhaeswim.dto;

import com.alijas.gimhaeswim.domain.Competition;
import com.alijas.gimhaeswim.domain.CompetitionEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompetitionDto {
    private String competitionName;
    private String year;
    private String competitionPeriodStart;
    private String competitionPeriodEnd;
    private String detail;
    private String place;
    private String price;
    private List<CompetitionEvent> competitionEventList;
}
