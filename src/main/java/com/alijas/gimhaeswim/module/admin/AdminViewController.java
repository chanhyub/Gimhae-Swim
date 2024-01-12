package com.alijas.gimhaeswim.module.admin;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.request.CompetitionSaveRequest;
import com.alijas.gimhaeswim.module.competition.service.*;
import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.service.HistoryService;
import com.alijas.gimhaeswim.module.notice.dto.NoticeDTO;
import com.alijas.gimhaeswim.module.notice.dto.NoticeListDTO;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.service.NoticeService;
import com.alijas.gimhaeswim.module.user.dto.UserAdminDTO;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.service.UserService;
import com.alijas.gimhaeswim.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final UserService userService;

    private final CompetitionService competitionService;

    private final CompetitionEventService competitionEventService;

    private final DepartmentService departmentService;

    private final EventService eventService;

    private final MeterService meterService;

    private final NoticeService noticeService;

    private final HistoryService historyService;


    public AdminViewController(UserService userService, CompetitionService competitionService, CompetitionEventService competitionEventService, DepartmentService departmentService, EventService eventService, MeterService meterService, NoticeService noticeService, HistoryService historyService) {
        this.userService = userService;
        this.competitionService = competitionService;
        this.competitionEventService = competitionEventService;
        this.departmentService = departmentService;
        this.eventService = eventService;
        this.meterService = meterService;
        this.noticeService = noticeService;
        this.historyService = historyService;
    }

    @GetMapping({"/", "", "/users"})
    public String getUserList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Page<UserAdminDTO> userPage = userService.getUserList(pageable, ApplyStatus.APPROVED, UserStatus.ACTIVE);
        PageUtil.set(pageable, model, userPage.getTotalPages());
        model.addAttribute("userPage", userPage);

        return "admin/main";
    }

    @GetMapping("/users/apply")
    public String getApproveUserList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Page<UserAdminDTO> userPage = userService.getUserList(pageable, ApplyStatus.WAITING, UserStatus.ACTIVE);
        PageUtil.set(pageable, model, userPage.getTotalPages());
        model.addAttribute("userPage", userPage);

        return "admin/userApprove";
    }

    @GetMapping("/competitions")
    public String getCompetitionList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Page<CompetitionListDTO> competitionListDTOS = competitionService.findAll(pageable);
        PageUtil.set(pageable, model, competitionListDTOS.getTotalPages());
        model.addAttribute("competitionListDTOS", competitionListDTOS);

        return "admin/competitionList";
    }

    @GetMapping("/competitions/save")
    public String getCompetitionSave(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

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
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id,
            Model model
    ) {
        if (customUserDetails == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

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

    @GetMapping("/notices")
    public String getNoticeList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Page<NoticeDTO> noticePage = noticeService.findAllAdmin(pageable);
        PageUtil.set(pageable, model, noticePage.getTotalPages());
        model.addAttribute("noticePage", noticePage);

        return "admin/notice";
    }

    @GetMapping("/notices/save")
    public String getNoticeSave(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        return "admin/noticeSave";
    }

    @GetMapping("/notices/{id}/update")
    public String getNoticeUpdate(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model,
            @PathVariable Long id) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Optional<Notice> optionalNotice = noticeService.getNotice(id);
        if (optionalNotice.isEmpty()) {
            throw new CustomException("존재하지 않는 공지사항입니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("notice", optionalNotice.get().toDTO());

        return "admin/noticeUpdate";
    }

    @GetMapping("/histories")
    public String getHistoryList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model,
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Page<History> historyList = historyService.getHistoryList(pageable);
        PageUtil.set(pageable, model, historyList.getTotalPages());
        model.addAttribute("historyPage", historyList);

        return "admin/history";
    }

    @GetMapping("/histories/save")
    public String getHistorySave(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        return "admin/historySave";
    }

    @GetMapping("/histories/{id}/update")
    public String getHistoryUpdate(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model,
            @PathVariable Long id
    ) {
        if (customUserDetails == null) {
            return "redirect:/login";
        }

        Optional<History> optionalHistory = historyService.getHistory(id);
        if (optionalHistory.isEmpty()) {
            throw new CustomException("존재하지 않는 연혁입니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("history", optionalHistory.get().toDTO());

        return "admin/historyUpdate";
    }

    @GetMapping("/photos")
    public String getPhotoList() {
        return "admin/photo";
    }

    @GetMapping("/photos/save")
    public String save() {
        return "admin/photoSave";
    }

}
