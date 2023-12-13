package com.alijas.gimhaeswim.module.applycompetition.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyCompetitionSaveRequest {
    String userId;

    String individualCompetitionEventIds;

    String organizationCompetitionEventIds;

    String teamId;
}
