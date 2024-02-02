package com.alijas.gimhaeswim.module.admin;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.service.*;
import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.service.HistoryService;
import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.lane.response.LaneResponse;
import com.alijas.gimhaeswim.module.lane.service.LaneService;
import com.alijas.gimhaeswim.module.notice.dto.NoticeDTO;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.service.NoticeService;
import com.alijas.gimhaeswim.module.photo.dto.PhotoListDTO;
import com.alijas.gimhaeswim.module.photo.service.PhotoService;
import com.alijas.gimhaeswim.module.referee.dto.RefereeListDTO;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.request.RefereeSaveRequest;
import com.alijas.gimhaeswim.module.referee.service.RefereeService;
import com.alijas.gimhaeswim.module.section.entity.Section;
import com.alijas.gimhaeswim.module.section.response.SectionResponse;
import com.alijas.gimhaeswim.module.section.service.SectionService;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.user.dto.UserAdminDTO;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.service.UserService;
import com.alijas.gimhaeswim.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final UserService userService;

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final ApplyCompetitionService applyCompetitionService;

    private final ApplyCompetitionEventService applyCompetitionEventService;

    private final DepartmentService departmentService;

    private final EventService eventService;

    private final MeterService meterService;

    private final NoticeService noticeService;

    private final HistoryService historyService;

    private final RefereeService refereeService;

    private final TeamMemberService teamMemberService;

    private final SectionService sectionService;

    private final LaneService laneService;

    private final PhotoService photoService;

    public AdminViewController(UserService userService, CompetitionService competitionService, CompetitionEventService competitionEventService, ApplyCompetitionService applyCompetitionService, ApplyCompetitionEventService applyCompetitionEventService, DepartmentService departmentService, EventService eventService, MeterService meterService, NoticeService noticeService, HistoryService historyService, RefereeService refereeService, TeamMemberService teamMemberService, SectionService sectionService, LaneService laneService, PhotoService photoService) {
        this.userService = userService;
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.applyCompetitionService = applyCompetitionService;
        this.applyCompetitionEventService = applyCompetitionEventService;
        this.departmentService = departmentService;
        this.eventService = eventService;
        this.meterService = meterService;
        this.noticeService = noticeService;
        this.historyService = historyService;
        this.refereeService = refereeService;
        this.teamMemberService = teamMemberService;
        this.sectionService = sectionService;
        this.laneService = laneService;
        this.photoService = photoService;
    }

    @GetMapping({"/", "", "/users"})
    public String getUserList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        Page<UserAdminDTO> userPage = userService.getUserList(pageable, ApplyStatus.APPROVED, UserStatus.ACTIVE);
        PageUtil.set(pageable, model, userPage.getTotalPages());
        model.addAttribute("userPage", userPage);

        return "admin/main";
    }

    @GetMapping("/users/apply")
    public String getApproveUserList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        Page<UserAdminDTO> userPage = userService.getUserList(pageable, ApplyStatus.WAITING, UserStatus.ACTIVE);
        PageUtil.set(pageable, model, userPage.getTotalPages());
        model.addAttribute("userPage", userPage);

        return "admin/userApprove";
    }

    @GetMapping("/competitions")
    public String getCompetitionList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        Page<CompetitionListDTO> competitionListDTOS = competitionService.findAll(pageable);
        PageUtil.set(pageable, model, competitionListDTOS.getTotalPages());
        model.addAttribute("competitionListDTOS", competitionListDTOS);

        return "admin/competitionList";
    }

    @GetMapping("/competitions/save")
    public String getCompetitionSave(
            Model model
    ) {
        List<Department> departmentList = departmentService.findAll();
        List<Event> eventList = eventService.findAll();
        List<Meter> meterList = meterService.findAll();


//        List<String> departmentNameList = new ArrayList<>();
//
//        departmentList.forEach(department -> {
//            departmentNameList.add(department.getDepartmentName());
//        });

        model.addAttribute("departmentList", departmentList);
        model.addAttribute("eventList", eventList);
        model.addAttribute("meterList", meterList);

        return "admin/competitionSave";
    }

    @GetMapping("/competitions/{id}/update")
    public String getCompetition(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);
        if (optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        List<Department> departmentList = departmentService.findAll();
        List<Event> eventList = eventService.findAll();
        List<Meter> meterList = meterService.findAll();

//        Integer count = competitionEventService.getCompetitionEventCount(id);

        Competition competition = optionalCompetition.get();

        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetition(competition.getId());

        model.addAttribute("competition", competition.toCompetitionUpdateDTO());
        model.addAttribute("competitionEventList", competitionEventList);
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("eventList", eventList);
        model.addAttribute("meterList", meterList);
//        model.addAttribute("count", count);

        return "admin/competitionUpdate";
    }

    @GetMapping("/apply-competitions")
    public String getApplyCompetitionList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        model.addAttribute("applyCompetitionIndividualPage", applyCompetitionService.findAllByApplyStatusAndUserNotNull(pageable));
        model.addAttribute("applyCompetitionOrganizationPage", applyCompetitionService.findAllByApplyStatusAndTeamNotNull(pageable));

        return "admin/applyCompetitionManagement";
    }

    @GetMapping("/notices")
    public String getNoticeList(
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {
        Page<NoticeDTO> noticePage = noticeService.findAllAdmin(pageable);
        PageUtil.set(pageable, model, noticePage.getTotalPages());
        model.addAttribute("noticePage", noticePage);

        return "admin/notice";
    }

    @GetMapping("/notices/save")
    public String getNoticeSave(
            Model model
    ) {
        return "admin/noticeSave";
    }

    @GetMapping("/notices/{id}/update")
    public String getNoticeUpdate(
            Model model,
            @PathVariable Long id)
    {
        Optional<Notice> optionalNotice = noticeService.getNotice(id);
        if (optionalNotice.isEmpty()) {
            throw new CustomException("존재하지 않는 공지사항입니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("notice", optionalNotice.get().toDTO());

        return "admin/noticeUpdate";
    }

    @GetMapping("/histories")
    public String getHistoryList(
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {
        Page<History> historyList = historyService.getHistoryList(pageable);
        PageUtil.set(pageable, model, historyList.getTotalPages());
        model.addAttribute("historyPage", historyList);

        return "admin/history";
    }

    @GetMapping("/histories/save")
    public String getHistorySave(
            Model model
    ) {
        return "admin/historySave";
    }

    @GetMapping("/histories/{id}/update")
    public String getHistoryUpdate(
            Model model,
            @PathVariable Long id
    ) {

        Optional<History> optionalHistory = historyService.getHistory(id);
        if (optionalHistory.isEmpty()) {
            throw new CustomException("존재하지 않는 연혁입니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("history", optionalHistory.get().toDTO());

        return "admin/historyUpdate";
    }

    @GetMapping("/photos")
    public String getPhotoList(
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {

        Page<PhotoListDTO> photoPage = photoService.getPhotoPage(pageable);

        PageUtil.set(pageable, model, photoPage.getTotalPages());
        model.addAttribute("photoPage", photoPage);

        return "admin/photo";
    }

    @GetMapping("/photos/save")
    public String save() {
        return "admin/photoSave";
    }

    @GetMapping("/competitions/{id}/lanes")
    public String getLanes(
            @PathVariable Long id,
            Model model
    ) {

        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);
        if (optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = optionalCompetition.get();
        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetition(competition.getId());
        List<Referee> refereeList = refereeService.getRefereeList();

        model.addAttribute("competition", competition);
        model.addAttribute("competitionEventList", competitionEventList);
        model.addAttribute("refereeList", refereeList);

        return "admin/competitionLane";
    }

    @GetMapping("/competitions/{id}/lanes/set")
    public String getLanesSet(
            @PathVariable Long id,
            @RequestParam(value = "competitionEventId", required = true) Long competitionEventId,
            @RequestParam(value = "isTeam", required = true) Boolean isTeam,
            Model model
    ) {

        Optional<Competition> optionalCompetition = competitionService.getCompetition(id);
        if (optionalCompetition.isEmpty()) {
            throw new CustomException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        Competition competition = optionalCompetition.get();
        List<CompetitionEvent> competitionEventList = competitionEventService.getCompetitionEventByCompetition(competition.getId());
        List<Referee> refereeList = refereeService.getRefereeList();
        List<Team> teamList = new ArrayList<>();
        List<TeamMember> teamMemberList = new ArrayList<>();
        List<User> userList = new ArrayList<>();

        if(isTeam) {
            List<ApplyCompetitionEvent> applyCompetitionByCompetitionEvent = applyCompetitionEventService.getApplyCompetitionByCompetitionEvent(competitionEventId);

            applyCompetitionByCompetitionEvent.forEach(applyCompetitionEvent -> {
                if(applyCompetitionEvent.getApplyCompetition().getApplyStatus().name().equals("APPROVED")) {
                    if(applyCompetitionEvent.getTeam() != null) {
                        teamList.add(applyCompetitionEvent.getTeam());
                    }
                }
            });

            for (int i = 0; i < teamList.size(); i++) {
                teamMemberList.addAll(teamMemberService.getTeamMemberList(teamList.get(i)));
            }

            model.addAttribute("teamList", teamList);
            model.addAttribute("teamMemberList", teamMemberList);
        } else {
            List<ApplyCompetitionEvent> applyCompetitionByCompetitionEvent = applyCompetitionEventService.getApplyCompetitionByCompetitionEvent(competitionEventId);

            applyCompetitionByCompetitionEvent.forEach(applyCompetitionEvent -> {
                if(applyCompetitionEvent.getApplyCompetition().getApplyStatus().name().equals("APPROVED")) {
                    if(applyCompetitionEvent.getUser() != null) {
                        userList.add(applyCompetitionEvent.getUser());
                    }
                }
            });

            model.addAttribute("userList", userList);
        }

        List<SectionResponse> sectionResponseList = new ArrayList<>();

        List<Section> sectionList = sectionService.getSectionList(competitionEventId);
        sectionList.forEach(section -> {
            SectionResponse sectionResponse = new SectionResponse();
            sectionResponse.setId(section.getId());
            sectionResponse.setSectionNumber(section.getSectionNumber());

            List<Lane> laneList =  laneService.findBySection(section);
            List<LaneResponse> laneResponseList = new ArrayList<>();
            laneList.forEach(lane -> {
                laneResponseList.add(lane.toResponse());
            });

            sectionResponse.setLaneResponseList(laneResponseList);
            sectionResponseList.add(sectionResponse);
        });

        model.addAttribute("sectionResponseList", sectionResponseList);


        model.addAttribute("isTeam", isTeam);
        model.addAttribute("competition", competition);
        model.addAttribute("competitionEventList", competitionEventList);
        model.addAttribute("competitionEventId", competitionEventId);
        model.addAttribute("refereeList", refereeList);

        return "admin/competitionLaneSet";
    }

    @GetMapping("/referees")
    public String getRefereeList(
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {

        Page<RefereeListDTO> refereePage = refereeService.getRefereePage(pageable);
        PageUtil.set(pageable, model, refereePage.getTotalPages());
        model.addAttribute("refereePage", refereePage);

        return "admin/referee";
    }

    @GetMapping("/referees/save")
    public String getRefereeSave() {
        return "admin/refereeSave";
    }

    @PostMapping("/referees/add")
    public String addReferee(
            @Valid RefereeSaveRequest refereeSaveRequest, Errors errors
    ) {
        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if(!refereeSaveRequest.password().equals(refereeSaveRequest.confirmPassword())){
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        refereeService.addReferee(refereeSaveRequest);

        return "redirect:/admin/referees";
    }

}
