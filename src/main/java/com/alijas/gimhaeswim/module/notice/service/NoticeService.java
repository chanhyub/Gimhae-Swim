package com.alijas.gimhaeswim.module.notice.service;

import com.alijas.gimhaeswim.module.notice.dto.NoticeDTO;
import com.alijas.gimhaeswim.module.notice.dto.NoticeListDTO;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import com.alijas.gimhaeswim.module.notice.repository.NoticeRepository;
import com.alijas.gimhaeswim.module.notice.request.NoticeSaveRequest;
import com.alijas.gimhaeswim.module.notice.request.NoticeUpdateRequest;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Page<NoticeListDTO> findAll(Pageable pageable) {
        Page<Notice> page = noticeRepository.findAllByStatus(pageable, NoticeStatus.ACTIVE);
        return page.map(Notice::toListDTO);
    }

    public Page<NoticeDTO> findAllAdmin(Pageable pageable) {
        Page<Notice> page = noticeRepository.findAllByStatus(pageable, NoticeStatus.ACTIVE);
        return page.map(Notice::toDTO);
    }

    public Optional<Notice> getNotice(Long id) {

        return noticeRepository.findById(id);
    }

    @Transactional
    public void save(NoticeSaveRequest noticeSaveRequest, User user) {
        Notice notice = noticeSaveRequest.toEntity();
        notice.setUser(user);
        noticeRepository.save(notice);
    }

    @Transactional
    public void update(Notice notice, NoticeUpdateRequest noticeUpdateRequest) {
        notice.setTitle(noticeUpdateRequest.title());
        notice.setContent(noticeUpdateRequest.content());
        noticeRepository.save(notice);
    }

    public void delete(Notice notice) {
        noticeRepository.delete(notice);
    }
}
