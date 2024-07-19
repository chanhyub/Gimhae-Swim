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
import org.springframework.web.bind.annotation.RequestParam;

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
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 5) Pageable pageable, Model model,
            @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        Page<NoticeListDTO> noticePage;
        if (!searchType.isEmpty() && !keyword.isEmpty()) {
            if (searchType.equals("0")) {
                // 제목
                noticePage = noticeService.findAllBySearchToTitle(pageable, keyword);
            } else if (searchType.equals("1")) {
                // 내용
                noticePage = noticeService.findAllBySearchToContent(pageable, keyword);
            } else if (searchType.equals("2")) {
                // 제목 + 내용
                noticePage = noticeService.findAllBySearchToTitleAndContent(pageable, keyword);
            } else {
                throw new CustomException("잘못된 검색 조건입니다.", HttpStatus.BAD_REQUEST);
            }
//            Page<NoticeListDTO> noticePage = noticeService.findAllBySearch(pageable, searchType, keyword);
            PageUtil.set(pageable, model, noticePage.getTotalPages());
            model.addAttribute("noticePage", noticePage);
            model.addAttribute("searchType", searchType);
            model.addAttribute("keyword", keyword);

            return "notice/noticeList";
        }

        noticePage = noticeService.findAll(pageable);
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
