package com.alijas.gimhaeswim.module.competition.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomAuthenticationException;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.request.ApplyCompetitionIndividualSaveRequest;
import com.alijas.gimhaeswim.module.applycompetition.request.ApplyCompetitionOrganizationSaveRequest;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.service.CompetitionEventService;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/competitions")
public class CompetitionController {

    private final ApplyCompetitionService applyCompetitionService;

    private final ApplyCompetitionEventService applyCompetitionEventService;

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final UserService userService;

    private final TeamService teamService;

    public CompetitionController(ApplyCompetitionService applyCompetitionService1, ApplyCompetitionEventService applyCompetitionService, CompetitionService competitionService, CompetitionEventService competitionEventService, UserService userService, TeamService teamService) {
        this.applyCompetitionService = applyCompetitionService1;
        this.applyCompetitionEventService = applyCompetitionService;
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.userService = userService;
        this.teamService = teamService;
    }


    @PostMapping("/{competitionId}/apply")
    public String saveCompetitionEvent(
            @PathVariable Long competitionId,
            @Valid @ModelAttribute ApplyCompetitionIndividualSaveRequest applyCompetitionSaveRequest,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomAuthenticationException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userService.getUser(customUserDetails.getUser().getId());
        if (user.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = competitionService.getCompetition(competitionId)
                .orElseThrow(() -> new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST));

        if(errors.hasErrors()) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }


//        String[] applyIndividualCompetitionEvents = applyCompetitionSaveRequest.getIndividualCompetitionEventIds().split(",");
//        String[] applyOrganizationCompetitionEvents = applyCompetitionSaveRequest.getOrganizationCompetitionEventIds().split(",");

        List<String> individualCompetitionEventIds = List.of(applyCompetitionSaveRequest.getIndividualCompetitionEventIds().split(","));
        List<CompetitionEvent> individualCompetitionEvents = new ArrayList<>();

        individualCompetitionEventIds.forEach(
                individualCompetitionId -> {
                    individualCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(individualCompetitionId)).get());
                }
        );
//
//        for (String applyIndividualCompetitionEvent : individualCompetitionIds) {
//            individualCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(applyIndividualCompetitionEvent)).get());
//        }


        ApplyCompetition optionalApplyCompetition = applyCompetitionService.individualSave(competition, user.get());
        applyCompetitionEventService.individualSave(individualCompetitionEvents, user.get(), optionalApplyCompetition);

        return "redirect:/competitions";
    }

    @PostMapping("/{competitionId}/apply/teams")
    public String saveCompetitionEventTeam(
            @PathVariable Long competitionId,
            @Valid @ModelAttribute ApplyCompetitionOrganizationSaveRequest applyCompetitionOrganizationSaveRequest,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomAuthenticationException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userService.getUser(customUserDetails.getUser().getId());
        if (user.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Team> optionalTeam = teamService.getTeam(applyCompetitionOrganizationSaveRequest.getTeamId());
        if (optionalTeam.isEmpty()) {
            throw new CustomException("존재하지 않는 팀입니다.", HttpStatus.BAD_REQUEST);
        }

        Team team = optionalTeam.get();

        Competition competition = competitionService.getCompetition(competitionId)
                .orElseThrow(() -> new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST));

        if(errors.hasErrors()) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }


        List<CompetitionEvent> organizationCompetitionEvents = new ArrayList<>();
        List<String> organizationCompetitionEventIds = List.of(applyCompetitionOrganizationSaveRequest.getOrganizationCompetitionEventIds().split(","));

        organizationCompetitionEventIds.forEach(
                organizationCompetitionId -> {
                    organizationCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(organizationCompetitionId)).get());
                }
        );

//        for (String applyOrganizationCompetitionEvent : applyOrganizationCompetitionEvents) {
//            organizationCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(applyOrganizationCompetitionEvent)).get());
//        }

        ApplyCompetition applyCompetition = applyCompetitionService.organizationSave(competition, team);
        applyCompetitionEventService.organizationSave(organizationCompetitionEvents, user.get(), team, applyCompetition);

        return "redirect:/competitions";
    }

    @DeleteMapping("/{applyCompetitionId}/delete")
    @ResponseBody
    public ResponseEntity<String> deleteCompetition(
            @PathVariable Long applyCompetitionId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomRestException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userService.getUser(customUserDetails.getUser().getId());
        if (user.isEmpty()) {
            throw new CustomRestException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }


        Optional<ApplyCompetition> optionalApplyCompetition = applyCompetitionService.getApplyCompetition(applyCompetitionId);
        if (optionalApplyCompetition.isEmpty()) {
            throw new CustomRestException("존재하지 않는 신청입니다.", HttpStatus.BAD_REQUEST);
        }

        ApplyCompetition applyCompetition = optionalApplyCompetition.get();
        applyCompetitionEventService.deleteApplyCompetitionEvent(applyCompetition);
        applyCompetitionService.deleteApplyCompetition(applyCompetition);

        return ResponseEntity.ok("삭제되었습니다.");
    }

}
