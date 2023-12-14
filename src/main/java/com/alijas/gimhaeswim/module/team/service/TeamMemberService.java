package com.alijas.gimhaeswim.module.team.service;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswim.module.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
                        TeamMemberPosition.valueOf(position)
                )
        );
    }

    public List<TeamMember> getTeamMember(Team team) {
        return teamMemberRepository.findByTeam(team);
    }
}
