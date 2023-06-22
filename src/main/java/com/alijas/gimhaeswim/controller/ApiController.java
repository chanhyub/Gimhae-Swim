package com.alijas.gimhaeswim.controller;

import com.alijas.gimhaeswim.domain.Competition;
import com.alijas.gimhaeswim.service.CompetitionService;
import com.alijas.gimhaeswim.service.RecordService;
import com.alijas.gimhaeswim.service.RefereeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {
    private final CompetitionService competitionService;
    private final RefereeService refereeService;
    private final RecordService recordService;

    @GetMapping("/competition/list")
    public List<Competition> getCompetitionList(){
        List<Competition> competitionList = competitionService.getCompetitionList();
        return competitionList;
    }

    @GetMapping("/login")
    public String login(String name, String pwd){
        String refereeName = refereeService.login(name, pwd);
        return refereeName;
    }

    @PostMapping("/send/record")
    public void sendRecord(String record, Integer laneSeq){
        recordService.sendRecord(record, laneSeq);
    }

    @PostMapping("/competition/end")
    public boolean endCompetition(Integer laneSectionSeq){
        return recordService.endCompetition(laneSectionSeq);
    }

    @PostMapping("/competition/total")
    public void totalCompetition(Integer competitionEventSeq){
        recordService.totalTeamScore(competitionEventSeq);
    }
}
