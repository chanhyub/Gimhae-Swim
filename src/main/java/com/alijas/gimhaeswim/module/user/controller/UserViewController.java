package com.alijas.gimhaeswim.module.user.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.user.dto.UserDTO;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserService userService;

    private final TeamMemberService teamMemberService;

    public UserViewController(UserService userService, TeamMemberService teamMemberService) {
        this.userService = userService;
        this.teamMemberService = teamMemberService;
    }

    @GetMapping("/my-page")
    public String getUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model) {

        if (customUserDetails == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        User user = customUserDetails.getUser();

        Optional<TeamMember> userTeam = teamMemberService.getUserTeam(user);
        if (userTeam.isPresent()) {
            model.addAttribute("team", userTeam.get().getTeam().getTeamName());
        } else {
            model.addAttribute("team", null);
        }

        model.addAttribute("user", user.toUserDTO());

        return "user/myPage";
    }
}
