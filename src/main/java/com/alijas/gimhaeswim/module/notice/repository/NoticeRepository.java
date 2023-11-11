package com.alijas.gimhaeswim.module.notice.repository;

import com.alijas.gimhaeswim.module.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
