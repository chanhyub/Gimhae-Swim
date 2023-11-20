package com.alijas.gimhaeswim.module.competition.repository;

import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Long> {
}
