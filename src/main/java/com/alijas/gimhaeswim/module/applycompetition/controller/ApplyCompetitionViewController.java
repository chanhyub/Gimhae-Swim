package com.alijas.gimhaeswim.module.applycompetition.controller;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionEventService;
import com.alijas.gimhaeswim.module.applycompetition.service.ApplyCompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/apply-competitions")
public class ApplyCompetitionViewController {

    private final ApplyCompetitionEventService applyCompetitionEventService;

    private final ApplyCompetitionService applyCompetitionService;

    public ApplyCompetitionViewController(ApplyCompetitionEventService applyCompetitionService, ApplyCompetitionService applyCompetitionService1) {
        this.applyCompetitionEventService = applyCompetitionService;
        this.applyCompetitionService = applyCompetitionService1;
    }

    @GetMapping({"/", ""})
    public String applyCompetitionList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {
        if (customUserDetails == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }

//        List<ApplyCompetition> applyCompetitionList = applyCompetitionService.getApplyCompetition(customUserDetails.getUser());
//        if (applyCompetitionList.isEmpty()) {
//            throw new CustomException("신청한 대회가 없습니다.", HttpStatus.BAD_REQUEST);
//        }
//
//        model.addAttribute("applyCompetitionList", applyCompetitionList);

        return "competitions/apply/competitionApplyList";
    }
}
