package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.*;
import com.alijas.gimhaeswim.dto.TeamDto;
import com.alijas.gimhaeswim.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final LaneRepository laneRepository;
    private final LaneSectionRepository laneSectionRepository;
    private final CompetitionEventRepository competitionEventRepository;
    private final TeamPointRepository teamPointRepository;

    public void sendRecord(String result, Integer laneSeq){
        Record record = new Record();
        Lane lane = laneRepository.findById(laneSeq).get();

        record.setLane(lane);
        record.setReferee(lane.getReferee());
        record.setUser(lane.getUser());
        record.setCompetitionEvent(lane.getLaneSection().getCompetitionEvent());
        record.setRecord(result);
        recordRepository.save(record);

        lane.setDone(true);
        laneRepository.save(lane);
    }

    public boolean endCompetition(Integer laneSectionSeq){
        LaneSection laneSection = laneSectionRepository.findById(laneSectionSeq).get();
        laneSection.setDone(true);
        laneSectionRepository.save(laneSection);
        CompetitionEvent competitionEvent = laneSection.getCompetitionEvent();
        List<LaneSection> laneSectionList = competitionEvent.getLaneSectionList();
        for(LaneSection obj : laneSectionList){
            boolean isDone = obj.isDone();
            if(!isDone){
                return false;
            }
        }
        return true;
    }

    public List<Record> getRecord(Integer competitionEventSeq){
        List<Record> recordList = recordRepository.getRecord(competitionEventSeq);
        return recordList;
    }

    public void totalTeamScore(Integer competitionEventSeq){
        List<Record> recordList = recordRepository.getRecord(competitionEventSeq);
        CompetitionEvent competitionEvent = competitionEventRepository.findById(competitionEventSeq).get();
        Competition competition = competitionEvent.getCompetition();
        for(int i = 0; i<recordList.size(); i++){
            if(i >= 8){
                break;
            }
            Record record = recordList.get(i);
            Team team = record.getUser().getTeam();
            TeamPoint teamPoint = new TeamPoint();
            switch (i) {
                case 0:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getFirstPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getFirstPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 1:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getSecondPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getSecondPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 2:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getThirdPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getThirdPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 3:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getForthPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getForthPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 4:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getFifthPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getFifthPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 5:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getSixthPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getSixthPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 6:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getSeventhPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getSeventhPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
                case 7:
                    teamPoint.setCompetition(competition);
                    teamPoint.setTeam(team);
                    if(competitionEvent.getEighthPoint().equals("")){
                        teamPoint.setPoint("0");
                    }else{
                        teamPoint.setPoint(competitionEvent.getEighthPoint());
                    }
                    teamPoint.setCompetitionEvent(competitionEvent);
                    teamPointRepository.save(teamPoint);
                    break;
            }
        }
    }

    public List<TeamDto> getTeamRank(Integer competitionSeq){
        List<TeamPoint> teamPointList = teamPointRepository.getTeamPoint(competitionSeq);
        // 동일한 Team 객체를 가진 팀의 점수 합산
        Map<Team, Integer> teamPointsMap = new HashMap<>();
        for (TeamPoint teamPoint : teamPointList) {
            Team team = teamPoint.getTeam();
            Integer point = Integer.valueOf(teamPoint.getPoint());
            teamPointsMap.put(team, teamPointsMap.getOrDefault(team, 0) + point);
        }

        // TeamDto 리스트로 변환
        List<TeamDto> teamDtoList = teamPointsMap.entrySet()
                .stream()
                .map(entry -> new TeamDto(entry.getKey().getTeamName(), entry.getValue()))
                .collect(Collectors.toList());
        teamDtoList = teamDtoList.stream().sorted(Comparator.comparing(TeamDto::getPoint).reversed()).collect(Collectors.toList());
        for (TeamDto teamDto : teamDtoList) {
            System.out.println("Team: " + teamDto.getTeamName() + ", Points: " + teamDto.getPoint());
        }
        return teamDtoList;
    }
}
