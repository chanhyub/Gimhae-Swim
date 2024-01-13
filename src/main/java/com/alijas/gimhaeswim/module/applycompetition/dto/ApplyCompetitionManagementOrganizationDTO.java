package com.alijas.gimhaeswim.module.applycompetition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyCompetitionManagementOrganizationDTO {
    private Long id;
    private String competitionName;
    private String teamName;
    private String leaderName;
    private String phoneNumber;
    private String depositStatus;
    private List<String> departmentList;
}
