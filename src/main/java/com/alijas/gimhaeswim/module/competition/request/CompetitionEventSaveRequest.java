package com.alijas.gimhaeswim.module.competition.request;


import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionEventSaveRequest {
    @NotBlank(message = "대회 유형을 입력해주세요.")
    private String eventType;
    @NotNull(message = "대회 부별을 입력해주세요.")
    private Long department;
    @NotNull(message = "대회 종목을 입력해주세요.")
    private Long eventName;
    @NotNull(message = "대회 미터를 입력해주세요.")
    private Long meter;
    @NotNull(message = "대회 정원을 입력해주세요.")
    private Integer eventCapacity;
    @NotNull(message = "대회 1등 점수를 입력해주세요.")
    private Integer firstScore;
    @NotNull(message = "대회 2등 점수를 입력해주세요.")
    private Integer secondScore;
    @NotNull(message = "대회 3등 점수를 입력해주세요.")
    private Integer thirdScore;
    @NotNull(message = "대회 4등 점수를 입력해주세요.")
    private Integer fourthScore;
    @NotNull(message = "대회 5등 점수를 입력해주세요.")
    private Integer fifthScore;
    @NotNull(message = "대회 6등 점수를 입력해주세요.")
    private Integer sixthScore;
    @NotNull(message = "대회 7등 점수를 입력해주세요.")
    private Integer seventhScore;
    @NotNull(message = "대회 8등 점수를 입력해주세요.")
    private Integer eighthScore;

    public CompetitionEvent toCompetitionEvent(Department department, Event eventName, Meter meter, Competition competition, EventType eventType) {
        return new CompetitionEvent(
                null,
                eventType,
                department,
                eventName,
                meter,
                eventCapacity,
                firstScore,
                secondScore,
                thirdScore,
                fourthScore,
                fifthScore,
                sixthScore,
                seventhScore,
                eighthScore,
                competition
        );
    }
}