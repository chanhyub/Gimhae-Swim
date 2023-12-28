package com.alijas.gimhaeswim.module.user.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.request.UserSaveRequest;
import com.alijas.gimhaeswim.module.user.request.UserUpdateRequest;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final TeamMemberService teamMemberService;

    private final TeamService teamService;

    public UserController(UserService userService, TeamMemberService teamMemberService, TeamService teamService) {
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(
            @RequestBody @Valid UserSaveRequest userSaveRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        userService.getUser(userSaveRequest.username()).ifPresent(user -> {
            throw new CustomRestException("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        });

        User user = userService.saveUser(userSaveRequest);

        if (!userSaveRequest.teamId().isEmpty()) {
            Optional<Team> team = teamService.getTeam(Long.parseLong(userSaveRequest.teamId()));
            if (team.isEmpty()) {
                throw new CustomRestException("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
            }

            List<TeamMember> teamMemberList = teamMemberService.getTeamMember(team.get());
            if (teamMemberList.isEmpty()) {
                teamMemberService.saveTeamMember(team.get(), user, "LEADER");
            } else {
                teamMemberService.saveTeamMember(team.get(), user, "MEMBER");
            }
        }

        return ResponseEntity.ok("경기인 신청이 완료되었습니다.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid UserUpdateRequest userUpdateRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(userUpdateRequest.username()).orElseThrow(() -> new CustomRestException("존재하지 않는 아이디입니다.", HttpStatus.BAD_REQUEST));

        userService.updateUser(user, userUpdateRequest);

        return ResponseEntity.ok("경기인 정보가 수정되었습니다.");
    }


}
