package com.alijas.gimhaeswim.module.notice.repository;

import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findAllByStatus(Pageable pageable, NoticeStatus status);

//    @Query("SELECT e FROM Notice e WHERE e.competitionDate > :startDate AND e.competitionDate < :endDate AND e.status = :status")
//    Page<Notice> findAllByStatusAnd(Pageable pageable, NoticeStatus noticeStatus, String searchType, String keyword);

    Page<Notice> findAllByStatusAndTitleContaining(Pageable pageable, NoticeStatus status, String title);

    Page<Notice> findAllByStatusAndContentContaining(Pageable pageable, NoticeStatus status, String content);

    Page<Notice> findAllByStatusAndTitleContainingOrContentContaining(Pageable pageable, NoticeStatus status, String title, String content);
}
