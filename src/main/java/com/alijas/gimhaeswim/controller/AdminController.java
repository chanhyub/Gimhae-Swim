package com.alijas.gimhaeswim.controller;

import com.alijas.gimhaeswim.domain.*;
import com.alijas.gimhaeswim.dto.*;
import com.alijas.gimhaeswim.service.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final CompetitionService competitionService;
    private final RefereeService refereeService;
    private final NoticeService noticeService;
    private final PhotoService photoService;
    private final RecordService recordService;
    private final HistoryService historyService;

    @GetMapping
    public ModelAndView admin(){
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(String id, String pwd){

        if (id.equals("admin")&&pwd.equals("admin1003")){
            return "success";
        }
        else {
            return "fail";
        }
    }

    @GetMapping("/user/list")
    public ModelAndView userList(){
        ModelAndView modelAndView = new ModelAndView("admin/main");
        List<User> userList = adminService.getUserListIsDone();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

    @GetMapping("/user/list/register")
    public ModelAndView userListNotDone(){
        ModelAndView modelAndView = new ModelAndView("admin/sub1_register");
        List<User> userList = adminService.getUserListIsNotDone();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestParam("sex")String sex,
                                          @RequestParam("name")String name,
                                          @RequestParam("pwd")String pwd,
                                          @RequestParam("pwdCheck")String pwdCheck,
                                          @RequestParam("birthday")String birthday,
                                          @RequestParam("phoneNum")String phoneNum,
                                          @RequestParam("email")String email,
                                          @RequestParam("team_seq")String teamSeq){
        String result = adminService.createUser(sex, name, pwd, pwdCheck, birthday, phoneNum, email, teamSeq);
        if (result.equals("success")){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        } else{
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/register"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }

    }

    @PostMapping("/user/accept/{userSeq}")
    public void acceptUser(@PathVariable String userSeq){
        adminService.acceptUser(userSeq);
    }

    @PostMapping("/user/delete/{userSeq}")
    public void deleteUser(@PathVariable String userSeq){
        adminService.deleteUser(userSeq);
    }

    @GetMapping("/user/find")
    public String findUser(String userName, String password, String competitionSeq){
        String userSeq = adminService.findUser(userName, password, competitionSeq);
        return userSeq;
    }

    @GetMapping("/competition/list")
    public ModelAndView competitionList(){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_contest");
        List<Competition> competitionList = competitionService.getCompetitionList();
        modelAndView.addObject("competitionList", competitionList);
        return modelAndView;
    }

    @GetMapping("/competition/request")
    public ModelAndView competitionRequest(){
        ModelAndView modelAndView = new ModelAndView("admin/sub3_request");
        List<ApplicationDetail> applicationDetailList = competitionService.getApplicationList();
        modelAndView.addObject("applicationDetailList", applicationDetailList);
        return modelAndView;
    }

    @GetMapping("/competition/add")
    public ModelAndView competitionAdd(){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_2_contestadd");
        List<CompetitionEvent> competitionEventList = new ArrayList<>();
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setCompetitionEventList(competitionEventList);
//        modelAndView.addObject("competitionEventList", competitionEventList);
        modelAndView.addObject("competitionDto", competitionDto);
        return modelAndView;
    }

    @PostMapping("/competition/register")
    public ResponseEntity<?> competitionRegister(CompetitionDto competitionDto){
        competitionService.createCompetition(competitionDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/competition/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/application/accept/{applicationDetailSeq}")
    public void acceptApplication(@PathVariable String applicationDetailSeq){
        competitionService.acceptApplication(applicationDetailSeq);
    }

    @PostMapping("/competition/delete/{competitionSeq}")
    public ResponseEntity<?> competitionDelete(@PathVariable("competitionSeq") String competitionSeq){
        competitionService.deleteCompetition(competitionSeq);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/competition/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/competition/set/lane")
    public ModelAndView setLane(String competitionSeq, String competitionEventSeq){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_contest_set");
        Competition competition = competitionService.getCompetition(competitionSeq);
        List<CompetitionEvent> competitionEventList = competition.getCompetitionEventList();
        CompetitionEvent competitionEvent = competitionService.getCompetitionEvent(competitionEventSeq);

        List<LaneSection> laneSectionList = adminService.getLaneSectionList(Integer.valueOf(competitionEventSeq));

        List<ApplicationDetail> applicationDetailList = competitionService.getApplicationListFromCompetition(competitionSeq);
        List<Referee> refereeList = refereeService.getRefereeList();

        SetLaneDto setLaneDto = new SetLaneDto();
        String isTeam = "";
        if(competitionEvent.getSex().equals("단체")){
            isTeam = "Y";
        }else{
            isTeam = "N";
        }

        modelAndView.addObject("competitionEventList", competitionEventList);
        modelAndView.addObject("competitionEvent", competitionEvent);
        modelAndView.addObject("competitionSeq", competitionSeq);
        modelAndView.addObject("refereeList", refereeList);
        modelAndView.addObject("setLaneDto", setLaneDto);
        modelAndView.addObject("laneSectionList", laneSectionList);
        modelAndView.addObject("applicationDetailList", applicationDetailList);
        modelAndView.addObject("competitionName", competition.getCompetitionName());
        modelAndView.addObject("isTeam", isTeam);
        return modelAndView;
    }

    @GetMapping("/competition/set/rank")
    public ModelAndView setRank(String competitionSeq, String competitionEventSeq){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_contest_rank");
        Competition competition = competitionService.getCompetition(competitionSeq);
        List<CompetitionEvent> competitionEventList = competition.getCompetitionEventList();
        CompetitionEvent competitionEvent = competitionService.getCompetitionEvent(competitionEventSeq);
        List<Record> recordList = recordService.getRecord(Integer.valueOf(competitionEventSeq));

        modelAndView.addObject("competitionEventList", competitionEventList);
        modelAndView.addObject("competitionEvent", competitionEvent);
        modelAndView.addObject("competitionSeq", competitionSeq);
        modelAndView.addObject("competitionEventSeq", competitionEventSeq);
        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("competitionName", competition.getCompetitionName());
        return modelAndView;
    }

    @GetMapping("/competition/set/rank/team")
    public ModelAndView setRankTeam(String competitionSeq){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_contest_rank_team");
        Competition competition = competitionService.getCompetition(competitionSeq);
        List<CompetitionEvent> competitionEventList = competition.getCompetitionEventList();
        List<TeamDto> teamDtoList = recordService.getTeamRank(Integer.valueOf(competitionSeq));

        modelAndView.addObject("competitionEventList", competitionEventList);
        modelAndView.addObject("competitionSeq", competitionSeq);
        modelAndView.addObject("competitionName", competition.getCompetitionName());
        modelAndView.addObject("teamDtoList", teamDtoList);
        return modelAndView;
    }

    @PostMapping("/competition/set/lane")
    public String saveLane(String laneList, String competitionEventSeq, String isTeam) throws ParseException {
        adminService.saveLane(laneList, competitionEventSeq, isTeam);
        String result = "success";
        return result;
    }

    @GetMapping("/competition/update")
    public ModelAndView competitionUpdate(String competitionSeq){
        ModelAndView modelAndView = new ModelAndView("admin/sub2_3_contestupdate");
        Competition competition = competitionService.getCompetition(competitionSeq);
        CompetitionDto competitionDto = new CompetitionDto();
        modelAndView.addObject("competition", competition);
        modelAndView.addObject("competitionSeq", competitionSeq);
        modelAndView.addObject("competitionDto", competitionDto);
        return modelAndView;
    }

    @PostMapping("/competition/update")
    public ResponseEntity<?> competitionUpdatePut(CompetitionDto competitionDto, String competitionSeq){
        competitionService.updateCompetition(competitionDto, competitionSeq);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/competition/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/notice")
    public ModelAndView notice(){
        ModelAndView modelAndView = new ModelAndView("admin/sub4_notice");
        List<Notice> noticeList = noticeService.getNoticeList();
        modelAndView.addObject("noticeList", noticeList);
        return modelAndView;
    }

    @GetMapping("/notice/add")
    public ModelAndView noticeAdd(){
        ModelAndView modelAndView = new ModelAndView("admin/sub4_1_notice_add");
        NoticeDto noticeDto = new NoticeDto();
        modelAndView.addObject("noticeDto", noticeDto);
        return modelAndView;
    }

    @PostMapping("/notice/add")
    public ResponseEntity<?> doNoticeAdd(NoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/notice"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/notice/update")
    public ModelAndView noticeUpdate(Integer noticeSeq){
        Notice notice = noticeService.getNotice(noticeSeq);
        NoticeDto noticeDto = new NoticeDto();

        ModelAndView modelAndView = new ModelAndView("admin/sub4_2_notice_update");
        modelAndView.addObject("noticeTitle", notice.getNoticeTitle());
        modelAndView.addObject("noticeDetail", notice.getNoticeDetail());
        modelAndView.addObject("noticeSeq", notice.getNoticeSeq());
        modelAndView.addObject("noticeDto", noticeDto);
        return modelAndView;
    }

    @PostMapping("/notice/update")
    public ResponseEntity<?> doNoticeUpdate(Integer noticeSeq, NoticeDto noticeDto){
        noticeService.updateNotice(noticeSeq, noticeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/notice"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/notice/delete/{noticeSeq}")
    public void deleteNotice(@PathVariable("noticeSeq") String noticeSeq){
        Integer seq = Integer.valueOf(noticeSeq);
        noticeService.deleteNotice(seq);
    }

    @GetMapping("/photo")
    public ModelAndView photo(){
        ModelAndView modelAndView = new ModelAndView("admin/sub5_photo");
        List<Photo> photoList = photoService.getPhotoList();
        modelAndView.addObject("photoList", photoList);
        return modelAndView;
    }

    @GetMapping("/photo/add")
    public ModelAndView photoAdd(){
        ModelAndView modelAndView = new ModelAndView("admin/sub5_1_photo_add");
        PhotoDto photoDto = new PhotoDto();
        modelAndView.addObject("photoDto", photoDto);
        return modelAndView;
    }

    @PostMapping("/photo/add")
    public ResponseEntity<?> doPhotoAdd(PhotoDto photoDto) throws IOException {
        photoService.createPhoto(photoDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/photo"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/photo/delete/{photoSeq}")
    public void deletePhoto(@PathVariable("photoSeq") String photoSeq){
        Integer seq = Integer.valueOf(photoSeq);
        photoService.deletePhoto(seq);
    }

    @GetMapping("/history")
    public ModelAndView history(){
        ModelAndView modelAndView = new ModelAndView("admin/sub6_history");
        List<History> historyList = historyService.getHistoryList();
        modelAndView.addObject("historyList", historyList);
        return modelAndView;
    }

    @GetMapping("/history/add")
    public ModelAndView historyAdd(){
        ModelAndView modelAndView = new ModelAndView("admin/sub6_1_history_add");
        HistoryDto historyDto = new HistoryDto();
        modelAndView.addObject("historyDto", historyDto);
        return modelAndView;
    }

    @PostMapping("/history/add")
    public ResponseEntity<?> doHistoryAdd(HistoryDto historyDto) {
        historyService.createHistory(historyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/history"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/history/update")
    public ModelAndView historyUpdate(Integer historySeq){
        History history = historyService.getHistory(historySeq);
        HistoryDto historyDto = new HistoryDto();

        ModelAndView modelAndView = new ModelAndView("admin/sub6_2_history_update");
        modelAndView.addObject("year", history.getHistoryYear());
        modelAndView.addObject("month", history.getHistoryMonth());
        modelAndView.addObject("detail", history.getDetail());
        modelAndView.addObject("historySeq", history.getHistorySeq());
        modelAndView.addObject("historyDto", historyDto);
        return modelAndView;
    }

    @PostMapping("/history/delete/{historySeq}")
    public void deleteHistory(@PathVariable("historySeq") String historySeq){
        Integer seq = Integer.valueOf(historySeq);
        historyService.deleteHistory(seq);
    }

    @PostMapping("/history/update")
    public ResponseEntity<?> doHistoryUpdate(Integer historySeq, HistoryDto historyDto){
        historyService.updateHistory(historySeq, historyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/history"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/referee")
    public ModelAndView referee(){
        ModelAndView modelAndView = new ModelAndView("admin/sub7_referee");
        List<Referee> refereeList = refereeService.getRefereeList();
        modelAndView.addObject("refereeList", refereeList);
        return modelAndView;
    }

    @GetMapping("/referee/add")
    public ModelAndView refereeAdd(){
        ModelAndView modelAndView = new ModelAndView("admin/sub7_1_referee_add");
        RefereeDto refereeDto = new RefereeDto();
        modelAndView.addObject("refereeDto", refereeDto);
        return modelAndView;
    }

    @PostMapping("/referee/add")
    public ResponseEntity<?> doRefereeAdd(RefereeDto refereeDto){
        refereeService.createReferee(refereeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/referee"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/referee/update")
    public ModelAndView refereeUpdate(Integer refereeSeq){
        Referee referee = refereeService.getReferee(refereeSeq);
        RefereeDto refereeDto = new RefereeDto();

        ModelAndView modelAndView = new ModelAndView("admin/sub7_2_referee_update");
        modelAndView.addObject("name", referee.getRefereeName());
        modelAndView.addObject("password", referee.getPassword());
        modelAndView.addObject("refereeSeq", referee.getRefereeSeq());
        modelAndView.addObject("refereeDto", refereeDto);
        return modelAndView;
    }

    @PostMapping("/referee/update")
    public ResponseEntity<?> doRefereeUpdate(Integer refereeSeq, RefereeDto refereeDto){
        refereeService.updateReferee(refereeSeq, refereeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/referee"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/referee/delete/{refereeSeq}")
    public void deleteReferee(@PathVariable("refereeSeq") String refereeSeq){
        Integer seq = Integer.valueOf(refereeSeq);
        refereeService.deleteReferee(seq);
    }

    @GetMapping("/team/find")
    public String findTeam(String teamName){
        String teamSeq = adminService.findTeam(teamName);
        return teamSeq;
    }

    @GetMapping("/team/find/application")
    public String findTeamApplication(String teamName, String competitionSeq){
        String teamSeq = adminService.findTeamApplicationDetail(teamName, competitionSeq);
        return teamSeq;
    }

    @PostMapping("/create/team")
    public String createTeam(String teamName){
        Team team = adminService.createTeam(teamName);
        return String.valueOf(team.getTeamSeq());
    }

}
