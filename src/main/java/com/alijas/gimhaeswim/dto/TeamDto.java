package com.alijas.gimhaeswim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamDto {
    public String teamName;
    public Integer point;

    public TeamDto(String teamName, Integer point) {
        this.teamName = teamName;
        this.point = point;
    }
}
