package com.alijas.gimhaeswim.module.applycompetition.repository;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyCompetitionRepository extends JpaRepository<ApplyCompetition, Long> {

    Optional<ApplyCompetition> findByUserAndCompetitionEvent(User user, CompetitionEvent competitionEvent);
}
