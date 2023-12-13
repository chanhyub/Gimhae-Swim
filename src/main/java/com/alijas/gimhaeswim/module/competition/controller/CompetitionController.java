package com.alijas.gimhaeswim.module.competition.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.request.ApplyCompetitionSaveRequest;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.service.CompetitionEventService;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final UserService userService;

    private final TeamService teamService;

    public CompetitionController(ApplyCompetitionService applyCompetitionService, CompetitionService competitionService, CompetitionEventService competitionEventService, UserService userService, TeamService teamService) {
        this.applyCompetitionService = applyCompetitionService;
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.userService = userService;
        this.teamService = teamService;
    }


    // TODO :: 로그인 되면 User 객체 넣어야 함(Security)
    @PostMapping("/{competitionId}/apply")
    public String saveCompetitionEvent(
            @PathVariable Long competitionId,
            @Valid @ModelAttribute ApplyCompetitionSaveRequest applyCompetitionSaveRequest,
            Errors errors
    ) {
        Optional<User> user = userService.getUser(Long.parseLong(applyCompetitionSaveRequest.getUserId()));
        if (user.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Team> team = teamService.getTeam(Long.parseLong(applyCompetitionSaveRequest.getTeamId()));
        if (team.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        competitionService.getCompetition(competitionId)
                .orElseThrow(() -> new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST));

        if(errors.hasErrors()) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        String[] applyIndividualCompetitionEvents = applyCompetitionSaveRequest.getIndividualCompetitionEventIds().split(",");
        String[] applyOrganizationCompetitionEvents = applyCompetitionSaveRequest.getOrganizationCompetitionEventIds().split(",");

        List<CompetitionEvent> individualCompetitionEvents = new ArrayList<>();
        List<CompetitionEvent> organizationCompetitionEvents = new ArrayList<>();

        for (String applyIndividualCompetitionEvent : applyIndividualCompetitionEvents) {
            individualCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(applyIndividualCompetitionEvent)).get());
        }

        for (String applyOrganizationCompetitionEvent : applyOrganizationCompetitionEvents) {
            organizationCompetitionEvents.add(competitionEventService.getCompetitionEvent(Long.parseLong(applyOrganizationCompetitionEvent)).get());
        }

        applyCompetitionService.save(individualCompetitionEvents, organizationCompetitionEvents, user.get(), team.get());

        return "redirect:/competitions/" + competitionId + "/applications";
    }

}
