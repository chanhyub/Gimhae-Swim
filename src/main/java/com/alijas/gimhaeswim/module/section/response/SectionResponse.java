package com.alijas.gimhaeswim.module.section.response;

import com.alijas.gimhaeswim.module.lane.dto.LaneDTO;
import com.alijas.gimhaeswim.module.lane.response.LaneResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {
    private Long id;
    private Integer sectionNumber;

    private List<LaneResponse> laneResponseList;
}
