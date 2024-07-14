package com.alijas.gimhaeswim.module.team.repository;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberStatus;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeamAndStatus(Team team, TeamMemberStatus status);

    TeamMember findByTeamAndPositionAndStatus(Team team, TeamMemberPosition position, TeamMemberStatus status);

    Optional<TeamMember> findByUserAndStatus(User user, TeamMemberStatus status);

    Optional<TeamMember> findByIdAndStatus(Long teamMemberId, TeamMemberStatus teamMemberStatus);
}
