package com.alijas.gimhaeswim.module.team.controller;

import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService  teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/find")
    public ResponseEntity<String> findTeam(
            String teamName
    ) {
        Optional<Team> team = teamService.getTeam(teamName);
        if (team.isEmpty()) {
            throw new CustomRestException("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(team.get().getId().toString());
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTeam(
            String teamName
    ) {
        Team team = teamService.saveTeam(teamName);

        return ResponseEntity.ok(team.getId().toString());
    }
}
