package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
