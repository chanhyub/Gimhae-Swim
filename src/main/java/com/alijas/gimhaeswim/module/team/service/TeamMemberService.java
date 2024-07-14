package com.alijas.gimhaeswim.module.team.service;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberStatus;
import com.alijas.gimhaeswim.module.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @Transactional
    public void saveTeamMember(Team team, User user, String position) {
        teamMemberRepository.save(
                new TeamMember(
                        null,
                        team,
                        user,
                        TeamMemberPosition.valueOf(position),
                        TeamMemberStatus.ACTIVE
                )
        );
    }

    public List<TeamMember> getTeamMemberList(Team team) {
        return teamMemberRepository.findByTeamAndStatus(team, TeamMemberStatus.ACTIVE);
    }

    public Optional<TeamMember> getUserTeam(User user) {
        return teamMemberRepository.findByUserAndStatus(user, TeamMemberStatus.ACTIVE);
    }

    @Transactional
    public void deleteTeamMember(TeamMember teamMember) {
        teamMember.setStatus(TeamMemberStatus.DELETED);
        teamMemberRepository.save(teamMember);
    }

    public Optional<TeamMember> getTeamMember(Long teamMemberId) {
        return teamMemberRepository.findByIdAndStatus(teamMemberId, TeamMemberStatus.ACTIVE);
    }
}
