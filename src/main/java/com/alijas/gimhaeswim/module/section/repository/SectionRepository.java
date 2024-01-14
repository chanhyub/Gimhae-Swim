package com.alijas.gimhaeswim.module.section.repository;

import com.alijas.gimhaeswim.module.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCompetitionEventId(Long competitionEventId);
}
