package com.alijas.gimhaeswim.module.competition.repository;

import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Page<Competition> findAllByStatus(Pageable pageable, CompetitionStatus status);

    @Query("SELECT e FROM Competition e WHERE e.competitionDate > :startDate AND e.competitionDate < :endDate AND e.status = :status AND e.competitionName LIKE %:name%")
    Page<Competition> findAllByStatusAndYearAndCompetitionName(Pageable pageable, CompetitionStatus status, LocalDateTime startDate, LocalDateTime endDate, String name);

    @Query("SELECT e FROM Competition e WHERE e.competitionDate > :startDate AND e.competitionDate < :endDate AND e.status = :status")
    Page<Competition> findAllByStatusAndYear(Pageable pageable, CompetitionStatus status, LocalDateTime startDate, LocalDateTime endDate);

    Page<Competition> findAllByStatusAndCompetitionNameContaining(Pageable pageable, CompetitionStatus competitionStatus, String competitionName);
}
