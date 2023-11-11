package com.alijas.gimhaeswim.module.history.repository;

import com.alijas.gimhaeswim.module.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
