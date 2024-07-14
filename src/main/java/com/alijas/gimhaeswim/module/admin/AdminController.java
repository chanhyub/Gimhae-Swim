package com.alijas.gimhaeswim.module.admin;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import com.alijas.gimhaeswim.module.common.request.LoginRequest;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.request.CompetitionSaveRequest;
import com.alijas.gimhaeswim.module.competition.request.CompetitionUpdateRequest;
import com.alijas.gimhaeswim.module.competition.service.*;
import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.request.HistorySaveRequest;
import com.alijas.gimhaeswim.module.history.request.HistoryUpdateRequest;
import com.alijas.gimhaeswim.module.history.service.HistoryService;
import com.alijas.gimhaeswim.module.lane.service.LaneService;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.request.NoticeSaveRequest;
import com.alijas.gimhaeswim.module.notice.request.NoticeUpdateRequest;
import com.alijas.gimhaeswim.module.notice.service.NoticeService;
import com.alijas.gimhaeswim.module.photo.entity.Photo;
import com.alijas.gimhaeswim.module.photo.request.PhotoSaveRequest;
import com.alijas.gimhaeswim.module.photo.service.PhotoService;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.service.RefereeService;
import com.alijas.gimhaeswim.module.section.request.SectionSaveRequest;
import com.alijas.gimhaeswim.module.section.service.SectionService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final NoticeService noticeService;

    private final HistoryService historyService;

    private final PhotoService photoService;

    private final CompetitionService competitionService;

    private final ApplyCompetitionService applyCompetitionService;

    private final ApplyCompetitionEventService applyCompetitionEventService;

    private final SectionService sectionService;

    private final LaneService laneService;

    private final RefereeService refereeService;

    public AdminController(UserService userService, NoticeService noticeService, HistoryService historyService, PhotoService photoService, CompetitionService competitionService, ApplyCompetitionService applyCompetitionService, ApplyCompetitionEventService applyCompetitionEventService, SectionService sectionService, LaneService laneService, RefereeService refereeService) {
        this.userService = userService;
        this.noticeService = noticeService;
        this.historyService = historyService;
        this.photoService = photoService;
        this.competitionService = competitionService;
        this.applyCompetitionService = applyCompetitionService;
        this.applyCompetitionEventService = applyCompetitionEventService;
        this.sectionService = sectionService;
        this.laneService = laneService;
        this.refereeService = refereeService;
    }

    @PutMapping("/users/accept")
    public ResponseEntity<String> updateUser(
            @PathVariable String userId
    ) {
        Optional<User> optionalUser = userService.getUser(Long.parseLong(userId));
        if (optionalUser.isEmpty()) {
            throw new CustomRestException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        userService.updateUserStatus(user);

        return ResponseEntity.ok().body("승인되었습니다.");
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody Map<String, Long> userId
    ) {
        Optional<User> optionalUser = userService.getUser(userId.get("userId"));
        if (optionalUser.isEmpty()) {
            throw new CustomRestException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        userService.deleteUser(user);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }

    @PostMapping("/competitions")
    public ResponseEntity<String> saveCompetition(
            @RequestBody @Valid CompetitionSaveRequest competitionSaveRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        competitionService.saveCompetitionAndCompetitionEvent(competitionSaveRequest);

        return ResponseEntity.ok("대회가 등록되었습니다.");
    }

    @PutMapping("/competitions")
    public ResponseEntity<String> updateCompetition(
            @RequestBody @Valid CompetitionUpdateRequest competitionUpdateRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        competitionService.updateCompetitionAndCompetitionEvent(competitionUpdateRequest);



        return ResponseEntity.ok("대회가 수정되었습니다.");
    }

    @DeleteMapping("/competitions")
    public ResponseEntity<String> deleteCompetition(
            @RequestBody Map<String, Long> competitionId
    ) {
        Optional<Competition> optionalCompetition = competitionService.getCompetition(competitionId.get("competitionId"));
        if (optionalCompetition.isEmpty()) {
            throw new CustomRestException("존재하지 않는 대회입니다.", HttpStatus.BAD_REQUEST);
        }

        competitionService.deleteCompetition(optionalCompetition.get());

        return ResponseEntity.ok("대회가 삭제되었습니다.");
    }



    @PostMapping("/notices/save")
    public ResponseEntity<String> saveNotice(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody NoticeSaveRequest noticeSaveRequest,
            Errors errors
    ) {
        if (customUserDetails == null) {
            throw new CustomRestException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        if (errors.hasErrors()) {
            throw new CustomRestException("공지사항을 등록할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        noticeService.save(noticeSaveRequest, customUserDetails.getUser());
        return ResponseEntity.ok("공지사항이 등록되었습니다.");
    }

    @PutMapping("/notices/update")
    public ResponseEntity<String> updateNotice(
            @Valid @RequestBody NoticeUpdateRequest noticeUpdateRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<Notice> notice = noticeService.getNotice(Long.parseLong(noticeUpdateRequest.noticeId()));
        if (notice.isEmpty()) {
            throw new CustomRestException("존재하지 않는 공지사항입니다.", HttpStatus.BAD_REQUEST);
        }

        noticeService.update(notice.get(), noticeUpdateRequest);
        return ResponseEntity.ok("공지사항이 수정되었습니다.");
    }

    @DeleteMapping("/notices/delete")
    public ResponseEntity<String> deleteNotice(
            String noticeId
    ) {
        Optional<Notice> notice = noticeService.getNotice(Long.parseLong(noticeId));
        if (notice.isEmpty()) {
            throw new CustomRestException("존재하지 않는 공지사항입니다.", HttpStatus.BAD_REQUEST);
        }

        noticeService.delete(notice.get());
        return ResponseEntity.ok("공지사항이 삭제되었습니다.");
    }

    @PostMapping("/histories/save")
    public ResponseEntity<String> saveHistory(
            @RequestBody @Valid  HistorySaveRequest historySaveRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        historyService.save(historySaveRequest);

        return ResponseEntity.ok("연혁이 등록되었습니다.");
    }

    @PutMapping("/histories/update")
    public ResponseEntity<String> updateHistory(
            @RequestBody @Valid HistoryUpdateRequest historyUpdateRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<History> optionalHistory = historyService.getHistory(historyUpdateRequest.historyId());
        if (optionalHistory.isEmpty()) {
            throw new CustomRestException("존재하지 않는 연혁입니다.", HttpStatus.BAD_REQUEST);
        }

        historyService.update(optionalHistory.get(), historyUpdateRequest);

        return ResponseEntity.ok("연혁이 수정되었습니다.");
    }

    @DeleteMapping("/histories/delete")
    public ResponseEntity<String> deleteHistory(
            String historyId
    ) {
        Optional<History> optionalHistory = historyService.getHistory(Long.parseLong(historyId));
        if (optionalHistory.isEmpty()) {
            throw new CustomRestException("존재하지 않는 연혁입니다.", HttpStatus.BAD_REQUEST);
        }

        historyService.delete(optionalHistory.get());
        return ResponseEntity.ok("연혁이 삭제되었습니다.");
    }

    @PostMapping("/photos/save")
    public ResponseEntity<String> savePhoto(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            PhotoSaveRequest photoSaveRequest
    ) {
        if (customUserDetails == null) {
            throw new CustomRestException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

        photoService.save(photoSaveRequest, customUserDetails.getUser());


        return ResponseEntity.ok("포토갤러리 등록되었습니다.");
    }

    @DeleteMapping("/photo/{id}")
    public ResponseEntity<String> deletePhoto(
            @PathVariable Long id
    ) {
        Optional<Photo> optionalPhoto = photoService.getPhoto(id);
        if (optionalPhoto.isEmpty()) {
            throw new CustomRestException("존재하지 않는 포토갤러리입니다.", HttpStatus.BAD_REQUEST);
        }

        photoService.delete(optionalPhoto.get());
        return ResponseEntity.ok("포토갤러리가 삭제되었습니다.");
    }

    @PostMapping("/apply-competitions")
    public ResponseEntity<String> saveApplyCompetition(
            @RequestBody Map<String, Long> applyCompetitionId
    ) {
        Optional<ApplyCompetition> optionalApplyCompetition = applyCompetitionService.getApplyCompetition(applyCompetitionId.get("applyCompetitionId"));
        if (optionalApplyCompetition.isEmpty()) {
            throw new CustomRestException("존재하지 않는 신청입니다.", HttpStatus.BAD_REQUEST);
        }

        applyCompetitionService.apply(optionalApplyCompetition.get());

        return ResponseEntity.ok("대회 신청이 완료되었습니다.");
    }

    @DeleteMapping("/apply-competitions")
    public ResponseEntity<String> deleteApplyCompetition(
            String applyCompetitionId
    ) {
        Optional<ApplyCompetition> optionalApplyCompetition = applyCompetitionService.getApplyCompetition(Long.parseLong(applyCompetitionId));
        if (optionalApplyCompetition.isEmpty()) {
            throw new CustomRestException("존재하지 않는 신청입니다.", HttpStatus.BAD_REQUEST);
        }

        applyCompetitionEventService.deleteApplyCompetitionEvent(optionalApplyCompetition.get());
        applyCompetitionService.deleteApplyCompetition(optionalApplyCompetition.get());

        return ResponseEntity.ok("대회 신청이 취소되었습니다.");
    }

    @PostMapping("/competitions/lanes")
    public ResponseEntity<String> saveCompetitionLane(
            @RequestBody SectionSaveRequest sectionSaveRequest
    ) {
        sectionService.save(sectionSaveRequest);
        return ResponseEntity.ok("대회 레인이 등록되었습니다.");
    }

    @DeleteMapping("/referees/{id}")
    public ResponseEntity<String> deleteReferee(
            @PathVariable Long id
    ) {
        Optional<Referee> optionalReferee = refereeService.getReferee(id);
        if (optionalReferee.isEmpty()) {
            throw new CustomRestException("존재하지 않는 심판입니다.", HttpStatus.BAD_REQUEST);
        }

        Referee referee = optionalReferee.get();
        User user = referee.getUser();

        refereeService.delete(referee, user);
        return ResponseEntity.ok("심판이 삭제되었습니다.");
    }
}
