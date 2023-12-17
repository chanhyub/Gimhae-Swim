package com.alijas.gimhaeswim.module.applycompetition.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyCompetitionOrganizationSaveRequest {

    @NotBlank(message = "대회 신청할 팀을 선택해주세요.")
    String organizationCompetitionEventIds;

    @NotNull(message = "팀이 존재하지 않습니다.")
    Long teamId;
}
