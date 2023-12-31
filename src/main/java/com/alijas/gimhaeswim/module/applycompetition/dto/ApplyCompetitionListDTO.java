package com.alijas.gimhaeswim.module.applycompetition.dto;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApplyCompetitionListDTO {
     private Long id;

     private String competitionId;

     private String competitionName;

     private String depositStatus;

     private List<ApplyCompetitionEvent> applyCompetitionEventList;
}
