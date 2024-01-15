package com.alijas.gimhaeswim.module.lane.response;

import com.alijas.gimhaeswim.module.referee.dto.RefereeDTO;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.team.dto.TeamMemberDTO;
import com.alijas.gimhaeswim.module.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaneResponse {
    private Long id;
    private Integer laneNumber;
    private UserDTO user;
    private TeamMemberDTO teamMember;
    private RefereeDTO referee;
}
