package com.alijas.gimhaeswim.module.competition.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.request.ApplyCompetitionIndividualSaveRequest;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionEventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.dto.EventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.entity.Event;
import com.alijas.gimhaeswim.module.competition.service.CompetitionEventService;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.module.competition.service.EventService;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import com.alijas.gimhaeswim.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/competitions")
public class CompetitionViewController {

    final String INDIVIDUAL = "INDIVIDUAL";

    final String ORGANIZATION = "ORGANIZATION";

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final EventService eventService;

    private final UserService userService;

    private final ApplyCompetitionService applyCompetitionService;

    private final ApplyCompetitionEventService applyCompetitionEventService;

    private final TeamMemberService teamMemberService;

    public CompetitionViewController(CompetitionService competitionService, CompetitionEventService competitionEventService, EventService eventService, UserService userService, ApplyCompetitionService applyCompetitionService, ApplyCompetitionEventService applyCompetitionEventService, TeamMemberService teamMemberService) {
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.eventService = eventService;
        this.userService = userService;
        this.applyCompetitionService = applyCompetitionService;
        this.applyCompetitionEventService = applyCompetitionEventService;
        this.teamMemberService = teamMemberService;
    }

    @GetMapping({"/", ""})
    public String getCompetitionList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 5) Pageable pageable, Model model
    ) {
        Page<CompetitionListDTO> competitionPage = competitionService.findAll(pageable);
        PageUtil.set(pageable, model, competitionPage.getTotalPages());

        model.addAttribute("competitionPage", competitionPage);
        return "competitions/competitionList";
    }

    @GetMapping("/{id}")
    public String getCompetitionDetail(@PathVariable Long id, Model model) {
        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);

        if(optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = optionalCompetition.get();

        model.addAttribute("competition", competition.toCompetitionDetailDTO());

        return "competitions/competitionDetail";
    }

    @GetMapping("/{id}/applications")
    public String getCompetitionApply(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userService.getUser(customUserDetails.getUser().getId());
        if (optionalUser.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);
        if(optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }
        Competition competition = optionalCompetition.get();

        List<ApplyCompetition> applyCompetition = applyCompetitionService.getApplyCompetitionAndCompetition(user, competition);
        if (!applyCompetition.isEmpty()) {
            throw new CustomException("이미 신청한 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetitionIdAndType(competition.getId(), INDIVIDUAL);

        List<CompetitionEventListApplyDTO> competitionEventApplyDTOList = new ArrayList<>();

        competitionEventList.forEach(competitionEvent -> {
            competitionEventApplyDTOList.add(competitionEvent.toCompetitionEventListApplyDTO());
        });

        List<Event> eventList = eventService.getEventList();
        List<EventListApplyDTO> eventListApplyDTOList = new ArrayList<>();

        eventList.forEach(event -> {
            eventListApplyDTOList.add(event.toEventListApplyDTO());
        });

        model.addAttribute("competition", competition.toCompetitionApplyDTO());
        model.addAttribute("competitionEventList", competitionEventApplyDTOList);
        model.addAttribute("eventList", eventListApplyDTOList);
        model.addAttribute("applyCompetitionSaveRequest", new ApplyCompetitionIndividualSaveRequest());

        return "competitions/competitionApply";
    }

    @GetMapping("/{id}/applications/update")
    public String getCompetitionApplyUpdate(@PathVariable Long id, Model model) {
        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);

        if(optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = optionalCompetition.get();

        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetitionId(competition.getId(), INDIVIDUAL);
        List<CompetitionEventListApplyDTO> competitionEventApplyDTOList = new ArrayList<>();

        competitionEventList.forEach(competitionEvent -> {
            Optional<CompetitionEvent> optionalCompetitionEvent = competitionEventService.getCompetitionEvent(competitionEvent.getId());
            if (optionalCompetitionEvent.isEmpty()) {
                throw new CustomException("존재하지 않는 대회 종목이 존재합니다.", HttpStatus.BAD_REQUEST);
            }
            competitionEventApplyDTOList.add(competitionEvent.toCompetitionEventListApplyDTO());
        });

        List<Event> eventList = eventService.getEventList();
        List<EventListApplyDTO> eventListApplyDTOList = new ArrayList<>();

        eventList.forEach(event -> {
            eventListApplyDTOList.add(event.toEventListApplyDTO());
        });

        model.addAttribute("competition", competition.toCompetitionApplyDTO());
        model.addAttribute("competitionEventList", competitionEventApplyDTOList);
        model.addAttribute("eventList", eventListApplyDTOList);
        model.addAttribute("applyCompetitionSaveRequest", new ApplyCompetitionIndividualSaveRequest());

        return "competitions/competitionApplyUpdate";
    }

    @GetMapping("/{id}/applications/teams")
    public String getCompetitionApplyTeam(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userService.getUser(customUserDetails.getUser().getId());
        if (optionalUser.isEmpty()) {
            throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        Optional<TeamMember> optionalTeamMember = teamMemberService.getUserTeam(user);
        if (optionalTeamMember.isEmpty()) {
            throw new CustomException("팀이 없습니다.\n 팀을 생성하거나 가입 후 신청하세요.", HttpStatus.BAD_REQUEST);
        }

        TeamMember teamMember = optionalTeamMember.get();
        if (!teamMember.getPosition().name().equals("LEADER")) {
            throw new CustomException("팀장만 신청할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);
        if(optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }
        Competition competition = optionalCompetition.get();

        List<ApplyCompetition> applyCompetitionByTeam = applyCompetitionService.getApplyCompetitionByTeamAndCompetition(teamMember.getTeam(), competition);
        if (!applyCompetitionByTeam.isEmpty()) {
            throw new CustomException("이미 신청한 대회입니다.", HttpStatus.BAD_REQUEST);
        }


        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetitionIdAndType(competition.getId(), ORGANIZATION);
        List<CompetitionEventListApplyDTO> competitionEventApplyDTOList = new ArrayList<>();

        competitionEventList.forEach(competitionEvent -> {
            competitionEventApplyDTOList.add(competitionEvent.toCompetitionEventListApplyDTO());
        });

        List<Event> eventList = eventService.getEventList();
        List<EventListApplyDTO> eventListApplyDTOList = new ArrayList<>();

        eventList.forEach(event -> {
            eventListApplyDTOList.add(event.toEventListApplyDTO());
        });

        model.addAttribute("competition", competition.toCompetitionApplyDTO());
        model.addAttribute("competitionEventList", competitionEventApplyDTOList);
        model.addAttribute("eventList", eventListApplyDTOList);
        model.addAttribute("applyCompetitionSaveRequest", new ApplyCompetitionIndividualSaveRequest());
        model.addAttribute("team", teamMember.toDTO());

        return "competitions/competitionApplyTeam";
    }
}
