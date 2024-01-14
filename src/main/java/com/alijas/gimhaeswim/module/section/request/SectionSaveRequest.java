package com.alijas.gimhaeswim.module.section.request;

import com.alijas.gimhaeswim.module.lane.dto.LaneDTO;
import com.alijas.gimhaeswim.module.section.dto.SectionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionSaveRequest {
    private List<SectionDTO> saveRequest;
    private Long competitionEventId;

}
