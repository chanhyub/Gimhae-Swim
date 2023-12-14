package com.alijas.gimhaeswim.module.competition.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.request.ApplyCompetitionSaveRequest;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionEventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.dto.EventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.entity.Event;
import com.alijas.gimhaeswim.module.competition.service.CompetitionEventService;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.module.competition.service.EventService;
import com.alijas.gimhaeswim.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/competitions")
public class CompetitionViewController {

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final EventService eventService;

    public CompetitionViewController(CompetitionService competitionService, CompetitionEventService competitionEventService, EventService eventService) {
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.eventService = eventService;
    }

    @GetMapping({"/", ""})
    public String getCompetitionList(@PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 5) Pageable pageable, Model model) {
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
    public String getCompetitionApply(@PathVariable Long id, Model model) {
        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);

        if(optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = optionalCompetition.get();

        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetitionId(competition.getId());
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
        model.addAttribute("applyCompetitionSaveRequest", new ApplyCompetitionSaveRequest());

        return "/competitions/competitionApply";
    }
}
