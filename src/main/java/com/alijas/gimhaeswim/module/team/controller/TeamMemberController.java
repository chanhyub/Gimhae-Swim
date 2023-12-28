package com.alijas.gimhaeswim.module.team.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.request.TeamMemberSaveRequest;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teams/members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    private final TeamService teamService;

    public TeamMemberController(TeamMemberService teamMemberService, TeamService teamService) {
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTeamMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            String teamId
    ) {
        if (customUserDetails == null) {
            throw new CustomRestException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        User user = customUserDetails.getUser();

        Optional<Team> team = teamService.getTeam(Long.parseLong(teamId));
        if (team.isEmpty()) {
            throw new CustomRestException("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
        }

        List<TeamMember> teamMemberList = teamMemberService.getTeamMember(team.get());
        if (teamMemberList.isEmpty()) {
            teamMemberService.saveTeamMember(team.get(), user, "LEADER");
        } else {
            teamMemberService.saveTeamMember(team.get(), user, "MEMBER");
        }

        return ResponseEntity.ok("팀 가입이 완료되었습니다.");
    }
}
