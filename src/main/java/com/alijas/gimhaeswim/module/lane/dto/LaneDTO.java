package com.alijas.gimhaeswim.module.lane.dto;

import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.section.entity.Section;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaneDTO {
    private Integer laneNumber;
    private Long laneId;
    private Long userId;
    private Long teamMemberId;
    private Long refereeId;

    public Lane toEntity(User user, Referee referee, Section section) {
        return new Lane(null, laneNumber, user, null, referee, section);
    }

    public Lane toEntity(TeamMember teamMember, Referee referee, Section section) {
        return new Lane(null, laneNumber, null, teamMember, referee, section);
    }
}
