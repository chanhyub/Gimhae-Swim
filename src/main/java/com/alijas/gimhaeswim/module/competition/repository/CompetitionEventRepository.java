package com.alijas.gimhaeswim.module.competition.repository;

import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Long> {
    List<CompetitionEvent> findByCompetitionId(Long competitionId);
}
