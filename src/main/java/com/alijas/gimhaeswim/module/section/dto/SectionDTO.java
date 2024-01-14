package com.alijas.gimhaeswim.module.section.dto;

import com.alijas.gimhaeswim.module.lane.dto.LaneDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {
    private List<LaneDTO> sectionData;
    private Long sectionId;
}
