package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.*;
import com.alijas.gimhaeswim.dto.ApplicationDetailDto;
import com.alijas.gimhaeswim.dto.CompetitionDto;
import com.alijas.gimhaeswim.dto.UserDto;
import com.alijas.gimhaeswim.enums.Sex;
import com.alijas.gimhaeswim.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionEventRepository competitionEventRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ApplicationDetailRepository applicationDetailRepository;
    private final CompetitionEventApplicationDetailRepository competitionEventApplicationDetailRepository;


    public void createCompetition(CompetitionDto competitionDto){
        Competition competition = new Competition();
        competition.setYear(competitionDto.getYear());
        competition.setCompetitionName(competitionDto.getCompetitionName());
        competition.setCompetitionPeriod(competitionDto.getCompetitionPeriodStart() + " ~ " + competitionDto.getCompetitionPeriodEnd());
        competition.setDetail(competitionDto.getDetail());
        competition.setPlace(competitionDto.getPlace());
        competitionRepository.save(competition);

        for(CompetitionEvent competitionEvent : competitionDto.getCompetitionEventList()){
            competitionEvent.setCompetition(competition);
            competitionEventRepository.save(competitionEvent);
        }

    }

    public Competition getCompetition(String competitionSeq){
        Competition competition = competitionRepository.findById(Integer.valueOf(competitionSeq)).get();
        return competition;
    }

    public List<Competition> getCompetitionList(){
        List<Competition> competitionList = competitionRepository.findAll();
        return competitionList;
    }

    public void updateCompetition(CompetitionDto competitionDto, String competitionSeq){
        Competition competition = competitionRepository.findById(Integer.valueOf(competitionSeq)).get();
        competition.setYear(competitionDto.getYear());
        competition.setCompetitionName(competitionDto.getCompetitionName());
        competition.setCompetitionPeriod(competitionDto.getCompetitionPeriodStart() + " ~ " + competitionDto.getCompetitionPeriodEnd());
        competition.setDetail(competitionDto.getDetail());
        competition.setPlace(competitionDto.getPlace());
        competitionRepository.save(competition);


    }

    public void deleteCompetition(String competitionSeq){

        competitionRepository.deleteById(Integer.valueOf(competitionSeq));
    }

    public String applicationRegister(ApplicationDetailDto applicationDetailDto, List<String> competitionSeqList, List<String> teamCompetitionSeqList){
        String result = "fail";
        try {
            if(applicationDetailDto.getTeamSeq() == null) {
                User user = userRepository.findById(Integer.valueOf(applicationDetailDto.getUserSeq())).get();
                Competition competition = competitionRepository.findById(Integer.valueOf(applicationDetailDto.getCompetitionSeq())).get();

                ApplicationDetail applicationDetail = new ApplicationDetail();
                applicationDetail.setUser(user);
                applicationDetail.setCompetition(competition);
                applicationDetail.setDeposit(false);

                applicationDetailRepository.save(applicationDetail);

                for (String seq : competitionSeqList) {
                    CompetitionEventApplicationDetail competitionEventApplicationDetail = new CompetitionEventApplicationDetail();
                    CompetitionEvent competitionEvent = competitionEventRepository.findById(Integer.valueOf(seq)).get();
                    String sex = null;
                    if (user.getSex().equals(Sex.MALE)) {
                        sex = "남자";
                    } else if (user.getSex().equals(Sex.FEMALE)) {
                        sex = "여자";
                    }
                    if (!competitionEvent.getSex().equals(sex) && !competitionEvent.getSex().equals("단체")) {
                        System.out.println(sex);
                        result = "error";
                        return result;
                    }
                    competitionEventApplicationDetail.setCompetitionEvent(competitionEvent);
                    competitionEventApplicationDetail.setApplicationDetail(applicationDetail);
                    competitionEventApplicationDetailRepository.save(competitionEventApplicationDetail);
                }

                result = "success";
                return result;
            }else {
                User user = userRepository.findById(Integer.valueOf(applicationDetailDto.getUserSeq())).get();
                Competition competition = competitionRepository.findById(Integer.valueOf(applicationDetailDto.getCompetitionSeq())).get();

                ApplicationDetail applicationDetail = new ApplicationDetail();
                applicationDetail.setUser(user);
                applicationDetail.setCompetition(competition);
                applicationDetail.setDeposit(false);

                applicationDetailRepository.save(applicationDetail);

                for (String seq : competitionSeqList) {
                    CompetitionEventApplicationDetail competitionEventApplicationDetail = new CompetitionEventApplicationDetail();
                    CompetitionEvent competitionEvent = competitionEventRepository.findById(Integer.valueOf(seq)).get();
                    String sex = null;
                    if (user.getSex().equals(Sex.MALE)) {
                        sex = "남자";
                    } else if (user.getSex().equals(Sex.FEMALE)) {
                        sex = "여자";
                    }
                    if (!competitionEvent.getSex().equals(sex) && !competitionEvent.getSex().equals("단체")) {
                        System.out.println(sex);
                        result = "error";
                        return result;
                    }
                    competitionEventApplicationDetail.setCompetitionEvent(competitionEvent);
                    competitionEventApplicationDetail.setApplicationDetail(applicationDetail);
                    competitionEventApplicationDetailRepository.save(competitionEventApplicationDetail);

                    Team team = teamRepository.findById(Integer.valueOf(applicationDetailDto.getTeamSeq())).get();

                    ApplicationDetail teamApplicationDetail = new ApplicationDetail();
                    teamApplicationDetail.setTeam(team);
                    teamApplicationDetail.setCompetition(competition);
                    teamApplicationDetail.setDeposit(false);

                    applicationDetailRepository.save(teamApplicationDetail);

                    for (String teamSeq : teamCompetitionSeqList) {
                        CompetitionEventApplicationDetail teamCompetitionEventApplicationDetail = new CompetitionEventApplicationDetail();
                        CompetitionEvent teamCompetitionEvent = competitionEventRepository.findById(Integer.valueOf(seq)).get();
                        competitionEventApplicationDetail.setCompetitionEvent(teamCompetitionEvent);
                        competitionEventApplicationDetail.setApplicationDetail(teamApplicationDetail);
                        competitionEventApplicationDetailRepository.save(teamCompetitionEventApplicationDetail);
                    }
                    result = "success";
                    return result;
                }
            }
        } catch (Exception e){
            result = "fail";
            return result;
        }

        return result;
    }

    public void applicationDetailDelete(String applicationDetailSeq){
        applicationDetailRepository.deleteById(Integer.valueOf(applicationDetailSeq));
    }

    public List<ApplicationDetail> competitionCheck(UserDto userDto){
        User user = userRepository.getUser(userDto.getUserName(), userDto.getPassword());
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromUser(user.getUserSeq());
        return applicationDetailList;
    }

    public List<ApplicationDetail> getApplicationList(){
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.findAll();
        return applicationDetailList;
    }

    public List<ApplicationDetail> getApplicationListFromCompetition(String competitionSeq){
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromCompetition(Integer.valueOf(competitionSeq));
        return applicationDetailList;
    }

    public void acceptApplication(String applicationDetailSeq){
        ApplicationDetail applicationDetail = applicationDetailRepository.findById(Integer.valueOf(applicationDetailSeq)).get();
        applicationDetail.setDeposit(true);
        applicationDetailRepository.save(applicationDetail);
    }

    public CompetitionEvent getCompetitionEvent(String competitionEventSeq){
        CompetitionEvent competitionEvent = competitionEventRepository.findById(Integer.valueOf(competitionEventSeq)).get();
        return competitionEvent;
    }

}
