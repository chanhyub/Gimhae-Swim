package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.*;
import com.alijas.gimhaeswim.dto.CompetitionDto;
import com.alijas.gimhaeswim.enums.Sex;
import com.alijas.gimhaeswim.repository.*;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final CompetitionEventRepository competitionEventRepository;
    private final ApplicationDetailRepository applicationDetailRepository;
    private final UserRepository userRepository;
    private final LaneRepository laneRepository;
    private final LaneSectionRepository laneSectionRepository;
    private final RefereeRepository refereeRepository;
    private final TeamRepository teamRepository;

    public String createUser(String sex, String name, String pwd, String pwdCheck, String birthday, String phoneNum, String email, String team_seq){
        if(pwd.equals(pwdCheck)){
            User user = new User();
            user.setUserName(name);
            if(sex.equals("male")){
                user.setSex(Sex.MALE);
            }else if(sex.equals("female")){
                user.setSex(Sex.FEMALE);
            }
            user.setPassword(pwd);
            user.setBirthday(birthday);
            user.setPhoneNumber(phoneNum);
            user.setEmail(email);
            if(!team_seq.equals("")){
                Team team = teamRepository.findById(Integer.valueOf(team_seq)).get();
                user.setTeam(team);
                user.setOrganization(team.getTeamName());
            }
            user.setDone(false);
            userRepository.save(user);
            String result = "success";
            return result;

        }else{
            String result = "fail";
            return result;
        }
    }

    public List<User> getUserListIsDone(){
        List<User> userList = userRepository.getUserListIsDone();
        return userList;
    }

    public List<User> getUserListIsNotDone(){
        List<User> userList = userRepository.getUserListIsNotDone();
        return userList;
    }

    public void acceptUser(String userSeq){
        User user = userRepository.findById(Integer.valueOf(userSeq)).get();
        user.setDone(true);
    }

    public void deleteUser(String userSeq){
        userRepository.deleteById(Integer.valueOf(userSeq));
    }

    public String findUser(String userName, String pwd, String competitionSeq){
        User user = userRepository.getUser(userName, pwd);
        if(competitionSeq != null){
            List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromUserAndCompetition(user.getUserSeq(), Integer.valueOf(competitionSeq));

            if(applicationDetailList.isEmpty() || applicationDetailList == null){
                return "empty";
            }
        }
        return String.valueOf(user.getUserSeq());
    }

    public String findUserOnCompetition(String userName, String pwd, String competitionSeq){
        User user = userRepository.getUser(userName, pwd);
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromUserAndCompetition(user.getUserSeq(), Integer.valueOf(competitionSeq));

        if(!applicationDetailList.isEmpty()){
            return "isExist";
        }
        return String.valueOf(user.getUserSeq());
    }

    public String saveLane(String laneList, String competitionEventSeq, String isTeam) throws ParseException {
        List<LaneSection> laneSectionList = laneSectionRepository.getLaneSectionList(Integer.valueOf(competitionEventSeq));
        if(laneSectionList != null){
            for(LaneSection laneSection : laneSectionList){
                laneSectionRepository.delete(laneSection);
            }
        }
        JSONParser jsonParser = new JSONParser();
        CompetitionEvent competitionEvent = competitionEventRepository.findById(Integer.valueOf(competitionEventSeq)).get();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(laneList);
        for(Object object : jsonArray){
            LaneSection laneSection = new LaneSection();
            JSONObject jsonObject = (JSONObject) object;
            laneSection.setSectionName((String) jsonObject.get("section"));
            laneSection.setCompetitionEvent(competitionEvent);
            JSONArray laneArray = (JSONArray) jsonObject.get("laneData");
            laneSectionRepository.save(laneSection);
            for(Object o : laneArray){
                Lane lane = new Lane();
                JSONObject item = (JSONObject) o;
                lane.setLane((String) item.get("lane"));
                String refereeSeq = (String) item.get("referee");
                String teamSeq = (String) item.get("team");
                if(!(teamSeq == null)){
                    Team team = teamRepository.findById(Integer.valueOf(teamSeq)).get();
                    lane.setTeam(team);
                }
                String userSeq = (String) item.get("user");
                if(!(userSeq == null)){
                    User user = userRepository.findById(Integer.valueOf(userSeq)).get();
                    lane.setUser(user);
                }
                if(!(refereeSeq == null)){
                    Referee referee = refereeRepository.findById(Integer.valueOf(refereeSeq)).get();
                    lane.setReferee(referee);
                }
                lane.setLaneSection(laneSection);
                laneRepository.save(lane);
            }
        }
        return "success";
    }

    public List<LaneSection> getLaneSectionList(Integer competitionEventSeq){
        List<LaneSection> laneSectionList = laneSectionRepository.getLaneSectionList(competitionEventSeq);
        return laneSectionList;
    }

    public String findTeam(String teamName){
        Team team = teamRepository.getTeam(teamName);
        return String.valueOf(team.getTeamSeq());
    }

    public String findTeamApplicationDetail(String teamName, String competitionSeq){
        Team team = teamRepository.getTeam(teamName);
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromTeamAndCompetition(team.getTeamSeq(), Integer.valueOf(competitionSeq));
        if(!applicationDetailList.isEmpty()){
            return "isExist";
        }
        return String.valueOf(team.getTeamSeq());
    }

    public String findTeamApplicationDetailUpdate(String teamName, String competitionSeq){
        Team team = teamRepository.getTeam(teamName);
        List<ApplicationDetail> applicationDetailList = applicationDetailRepository.getApplicationDetailFromTeamAndCompetition(team.getTeamSeq(), Integer.valueOf(competitionSeq));
        if(applicationDetailList.isEmpty()){
            return "empty";
        }
        return String.valueOf(team.getTeamSeq());
    }

    public Team createTeam(String teamName){
        Team team = new Team();
        team.setTeamName(teamName);
        return teamRepository.save(team);
    }
}
