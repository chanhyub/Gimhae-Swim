package com.alijas.gimhaeswim.controller;

import com.alijas.gimhaeswim.domain.ApplicationDetail;
import com.alijas.gimhaeswim.domain.Competition;
import com.alijas.gimhaeswim.dto.ApplicationDetailDto;
import com.alijas.gimhaeswim.dto.UserDto;
import com.alijas.gimhaeswim.dto.UpdateApplicationDto;
import com.alijas.gimhaeswim.service.AdminService;
import com.alijas.gimhaeswim.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;
    private final AdminService adminService;

    @GetMapping
    public ModelAndView competition(){
        ModelAndView modelAndView = new ModelAndView("sub1_contest");
        List<Competition> competitionList = competitionService.getCompetitionList();
        modelAndView.addObject("competitionList", competitionList);
        return modelAndView;
    }

    @GetMapping("/detail")
    public ModelAndView competitionDetail(String competitionSeq){
        ModelAndView modelAndView = new ModelAndView("sub1_1_contest_more");
        Competition competition = competitionService.getCompetition(competitionSeq);
        modelAndView.addObject("competition", competition);
        return modelAndView;
    }

    @GetMapping("/application")
    public ModelAndView competitionApplication(String competitionSeq, String isDone){
        ModelAndView modelAndView = new ModelAndView("sub1_2_contest_request");
        Competition competition = competitionService.getCompetition(competitionSeq);
        ApplicationDetailDto applicationDetailDto = new ApplicationDetailDto();

        modelAndView.addObject("competition", competition);
        modelAndView.addObject("applicationDetailDto", applicationDetailDto);
        modelAndView.addObject("competitionEventList", competition.getCompetitionEventList());
        modelAndView.addObject("competitionSeq", competitionSeq);
        if(isDone.equals("fail")){
            modelAndView.addObject("isDone", isDone);
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping("/application/register")
    public ResponseEntity<?> competitionApplicationRegister(ApplicationDetailDto applicationDetailDto, @RequestParam(value = "competitionEventList")List<String> competitionEventList, @RequestParam(value = "teamCompetitionEventList", required = false)List<String> teamCompetitionEventList){
        if(teamCompetitionEventList == null){
            teamCompetitionEventList = new ArrayList<>();
        }
        String result = competitionService.applicationRegister(applicationDetailDto, competitionEventList, teamCompetitionEventList);

        HttpHeaders headers = new HttpHeaders();
        if(result.equals("success")){
            headers.setLocation(URI.create("/competition/end"));
        }else{
            headers.setLocation(URI.create("/competition/application?competitionSeq="+applicationDetailDto.getCompetitionSeq()+"&isDone="+result));
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    }

    @PostMapping("/application/delete/{applicationDetailSeq}")
    public void competitionApplicationDelete(@PathVariable("applicationDetailSeq") String applicationDetailSeq){
        competitionService.applicationDetailDelete(applicationDetailSeq);
    }

    @GetMapping("/end")
    public ModelAndView competitionApplicationEnd(){
        ModelAndView modelAndView = new ModelAndView("sub1_3_contest_request_end");
        return modelAndView;

    }

    @GetMapping("/confirm")
    public ModelAndView competitionConfirm(){
        ModelAndView modelAndView = new ModelAndView("sub2_confirmation");
        UserDto userDto = new UserDto();
        modelAndView.addObject("userDto", userDto);
        return modelAndView;

    }

    @GetMapping("/confirm/update")
    public ModelAndView competitionConfirmUpdate(String competitionSeq){
        ModelAndView modelAndView = new ModelAndView("sub2_confirmation_update");
        String setCompetitionSeq = competitionSeq;
        UpdateApplicationDto updateApplicationDto = new UpdateApplicationDto();
        modelAndView.addObject("updateApplicationDto", updateApplicationDto);
        modelAndView.addObject("setCompetitionSeq", setCompetitionSeq);
        return modelAndView;

    }

    @PostMapping("/confirm/check")
    public ModelAndView competitionConfirmCheck(UserDto userDto){
        ModelAndView modelAndView = new ModelAndView("sub2_1_confirmation_more");
        List<ApplicationDetail> applicationDetailList = competitionService.competitionCheck(userDto);

        modelAndView.addObject("applicationDetailList", applicationDetailList);
        return modelAndView;

    }

    @PostMapping("/application/update")
    public ModelAndView competitionApplicationUpdate(String isDone, UpdateApplicationDto updateApplicationDto){
        ModelAndView modelAndView = new ModelAndView("sub1_2_contest_request_update");
        Competition competition = competitionService.getCompetition(updateApplicationDto.getSetCompetitionSeq());
        if(isDone == null || isDone.trim().isEmpty()){
            isDone = "success";
        }
        UserDto userDto = new UserDto();
        userDto.setUserName(updateApplicationDto.getUserName());
        userDto.setPassword(updateApplicationDto.getPassword());

        List<ApplicationDetail> applicationDetailList = competitionService.competitionCheck(userDto);
        String userSeq = adminService.findUser(updateApplicationDto.getUserName(), updateApplicationDto.getPassword(), String.valueOf(competition.getCompetitionSeq()));
        ApplicationDetailDto applicationDetailDto = new ApplicationDetailDto();

        modelAndView.addObject("applicationDetailList", applicationDetailList);
        modelAndView.addObject("competition", competition);
        modelAndView.addObject("applicationDetailDto", applicationDetailDto);
        modelAndView.addObject("competitionEventList", competition.getCompetitionEventList());
        modelAndView.addObject("competitionSeq", updateApplicationDto.getSetCompetitionSeq());
        modelAndView.addObject("userSeq", userSeq);

        if(isDone.equals("fail")){
            modelAndView.addObject("isDone", isDone);
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping("/application/register/update")
    public ResponseEntity<?> competitionApplicationRegisterUpdate(ApplicationDetailDto applicationDetailDto, @RequestParam(value = "competitionEventList")List<String> competitionEventList, @RequestParam(value = "teamCompetitionEventList", required = false)List<String> teamCompetitionEventList){
        if(teamCompetitionEventList == null){
            teamCompetitionEventList = new ArrayList<>();
        }
        String result = competitionService.applicationRegisterUpdate(applicationDetailDto, competitionEventList, teamCompetitionEventList);

        HttpHeaders headers = new HttpHeaders();
        if(result.equals("success")){
            headers.setLocation(URI.create("/competition/end"));
        }else{
            headers.setLocation(URI.create("/competition/application/update?competitionSeq="+applicationDetailDto.getCompetitionSeq()+"&isDone="+result));
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    }
}
