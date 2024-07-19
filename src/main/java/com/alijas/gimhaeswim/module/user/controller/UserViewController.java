package com.alijas.gimhaeswim.module.user.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomAuthenticationException;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.dto.ApplyCompetitionListDTO;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserService userService;

    private final TeamMemberService teamMemberService;

    private final ApplyCompetitionService applyCompetitionService;

    private final ApplyCompetitionEventService applyCompetitionEventService;

    public UserViewController(UserService userService, TeamMemberService teamMemberService, ApplyCompetitionService applyCompetitionService, ApplyCompetitionEventService applyCompetitionEventService) {
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.applyCompetitionService = applyCompetitionService;
        this.applyCompetitionEventService = applyCompetitionEventService;
    }

    @GetMapping("/my-page")
    public String getUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model) {

        if (customUserDetails == null) {
            throw new CustomAuthenticationException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        User user = customUserDetails.getUser();

        Optional<TeamMember> userTeam = teamMemberService.getUserTeam(user);
        if (userTeam.isPresent()) {
            TeamMember teamMember = userTeam.get();
            if (teamMember.getPosition().name().equals("LEADER")) {
                model.addAttribute("isLeader", true);
            } else {
                model.addAttribute("isLeader", false);
            }
            List<TeamMember> teamMemberList = teamMemberService.getTeamMemberList(teamMember.getTeam());
            model.addAttribute("team", teamMemberList);

//            List<ApplyCompetition> applyCompetitionList = applyCompetitionService.getApplyCompetition(user, teamMember.getTeam());
            List<ApplyCompetition> applyCompetitionIndividualList = applyCompetitionService.getApplyCompetition(user);
            if (applyCompetitionIndividualList.isEmpty()) {
                model.addAttribute("applyCompetitionIndividualList", applyCompetitionIndividualList);
            } else {
                model.addAttribute("applyCompetitionIndividualList", applyCompetitionIndividualList);
            }

            List<ApplyCompetition> applyCompetitionOrganizationList = applyCompetitionService.getApplyCompetitionByTeam(teamMember.getTeam());
            if (applyCompetitionOrganizationList.isEmpty()) {
                model.addAttribute("applyCompetitionOrganizationList", applyCompetitionOrganizationList);
            } else {
                model.addAttribute("applyCompetitionOrganizationList", applyCompetitionOrganizationList);
            }


            List<ApplyCompetitionEvent> applyCompetitionEventList = applyCompetitionEventService.getApplyCompetitionByUserOrTeam(user, teamMember.getTeam());
//            List<ApplyCompetitionEvent> applyCompetitionEventIndividualList = applyCompetitionEventService.getApplyCompetitionByUser(user);
            if (applyCompetitionEventList.isEmpty()) {
                model.addAttribute("applyCompetitionEventList", null);
            } else {
                model.addAttribute("applyCompetitionEventList", applyCompetitionEventList);
            }

            List<ApplyCompetitionEvent> applyCompetitionEventOrganizationList = applyCompetitionEventService.getApplyCompetitionByTeam(teamMember.getTeam());

        } else {
            List<ApplyCompetition> applyCompetition = applyCompetitionService.getApplyCompetition(user);
            if (applyCompetition.isEmpty()) {
                model.addAttribute("applyCompetitionList", null);
            } else {
                model.addAttribute("applyCompetitionList", applyCompetition);
            }

            List<ApplyCompetitionEvent> applyCompetitionEventList = applyCompetitionEventService.getApplyCompetitionByUser(user);
            if (applyCompetitionEventList.isEmpty()) {
                model.addAttribute("applyCompetitionEventList", null);
            } else {
                model.addAttribute("applyCompetitionEventList", applyCompetitionEventList);
            }

            model.addAttribute("team", null);
        }

        model.addAttribute("user", user.toUserDTO());

        return "user/myPage";
    }
}
