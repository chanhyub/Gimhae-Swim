package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.CompetitionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Integer> {
}
