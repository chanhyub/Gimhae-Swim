package com.alijas.gimhaeswim.module.team.service;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> getTeam(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Optional<Team> getTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    public Team saveTeam(String teamName) {
        return teamRepository.save(
                new Team(
                        null,
                        teamName
                )
        );
    }

}

