package com.alijas.gimhaeswim.module.competition.repository;

import com.alijas.gimhaeswim.module.competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
