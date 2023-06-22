package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.Notice;
import com.alijas.gimhaeswim.dto.NoticeDto;
import com.alijas.gimhaeswim.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void createNotice(NoticeDto noticeDto){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        Notice notice = new Notice();
        notice.setNoticeTitle(noticeDto.getNoticeTitle());
        notice.setNoticeDetail(noticeDto.getNoticeDetail());
        notice.setRegistrationDate(formatter.format(date));
        noticeRepository.save(notice);
    }
    public Notice getNotice(Integer noticeSeq){
        Notice notice = noticeRepository.findById(noticeSeq).get();
        return notice;
    }
    public List<Notice> getNoticeList(){
        List<Notice> noticeList = noticeRepository.findAll();
        return noticeList;
    }
    public void updateNotice(Integer noticeSeq, NoticeDto noticeDto){
        Notice notice = noticeRepository.findById(noticeSeq).get();
        notice.setNoticeTitle(noticeDto.getNoticeTitle());
        notice.setNoticeDetail(noticeDto.getNoticeDetail());
        noticeRepository.save(notice);
    }
    public void deleteNotice(Integer noticeSeq){
        noticeRepository.deleteById(noticeSeq);
    }

}
