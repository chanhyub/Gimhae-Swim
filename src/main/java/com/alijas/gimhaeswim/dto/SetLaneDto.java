package com.alijas.gimhaeswim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SetLaneDto {
    private List<String> sectionList;
    private List<String> laneNameList;
    private List<String> userList;
    private List<String> refereeList;
}
