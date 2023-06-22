package com.alijas.gimhaeswim.controller;

import com.alijas.gimhaeswim.domain.*;
import com.alijas.gimhaeswim.service.CompetitionService;
import com.alijas.gimhaeswim.service.HistoryService;
import com.alijas.gimhaeswim.service.NoticeService;
import com.alijas.gimhaeswim.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {
    private final CompetitionService competitionService;
    private final NoticeService noticeService;
    private final PhotoService photoService;
    private final HistoryService historyService;

    @GetMapping
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Competition> competitionList = competitionService.getCompetitionList();
        List<Notice> noticeList = noticeService.getNoticeList();
        List<Photo> photoList = photoService.getPhotoList();
        Collections.reverse(noticeList);
        modelAndView.addObject("photoList", photoList);
        modelAndView.addObject("noticeList", noticeList);
        modelAndView.addObject("competitionList", competitionList);
        return modelAndView;
    }

    @GetMapping("/notice")
    public ModelAndView notice(){
        ModelAndView modelAndView = new ModelAndView("sub6_notice");
        List<Notice> noticeList = noticeService.getNoticeList();
        modelAndView.addObject("noticeList", noticeList);
        return modelAndView;
    }

    @GetMapping("/notice/detail")
    public ModelAndView noticeDetail(Integer noticeSeq){
        ModelAndView modelAndView = new ModelAndView("sub6_1_notice_more");
        Notice notice = noticeService.getNotice(noticeSeq);
        modelAndView.addObject("notice", notice);
        return modelAndView;
    }

    @GetMapping("/photo")
    public ModelAndView photo(){
        ModelAndView modelAndView = new ModelAndView("sub7_photo");
        List<Photo> photoList = photoService.getPhotoList();
        modelAndView.addObject("photoList", photoList);
        return modelAndView;
    }

    @GetMapping("/photo/detail")
    public ModelAndView photoDetail(Integer photoSeq){
        ModelAndView modelAndView = new ModelAndView("sub7_1_photo_more");
        Photo photo = photoService.getPhoto(photoSeq);
        List<PhotoImage> photoImageList = photo.getPhotoImageList();
        modelAndView.addObject("photo", photo);
        modelAndView.addObject("photoImageList", photoImageList);
        return modelAndView;
    }

    @GetMapping("/history")
    public ModelAndView history(){
        ModelAndView modelAndView = new ModelAndView("sub4_history");
        List<History> historyList = historyService.getHistoryList();
        modelAndView.addObject("historyList", historyList);
        return modelAndView;
    }



}
