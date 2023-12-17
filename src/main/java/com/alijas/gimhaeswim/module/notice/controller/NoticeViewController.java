package com.alijas.gimhaeswim.module.notice.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.notice.dto.NoticeListDTO;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.service.NoticeService;
import com.alijas.gimhaeswim.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/notices")
public class NoticeViewController {

    private final NoticeService noticeService;

    public NoticeViewController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping({"", "/"})
    public String getNoticeList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 5) Pageable pageable, Model model
    ) {
        Page<NoticeListDTO> noticePage = noticeService.findAll(pageable);
        PageUtil.set(pageable, model, noticePage.getTotalPages());
        model.addAttribute("noticePage", noticePage);

        return "notice/noticeList";
    }

    @GetMapping("/{id}")
    public String getNotice(
            @PathVariable Long id,
            Model model) {
        Optional<Notice> notice = noticeService.getNotice(id);
        if (notice.isEmpty()) {
            throw new CustomException("존재하지 않는 공지사항입니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("notice", notice.get().toDTO());
        return "notice/noticeDetail";
    }

}
