package com.alijas.gimhaeswim.dto;

import com.alijas.gimhaeswim.domain.Competition;
import com.alijas.gimhaeswim.domain.CompetitionEvent;
import com.alijas.gimhaeswim.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDetailDto {
    private String competitionSeq;
    private String userSeq;
    private String teamSeq;
}
