package com.alijas.gimhaeswim.module.notice.service;

import com.alijas.gimhaeswim.module.notice.dto.NoticeListDTO;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import com.alijas.gimhaeswim.module.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Optional<Notice> getNotice(Long id) {

        return noticeRepository.findById(id);
    }
}
