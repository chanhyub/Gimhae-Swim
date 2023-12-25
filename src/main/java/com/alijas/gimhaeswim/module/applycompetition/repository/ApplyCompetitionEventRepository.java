package com.alijas.gimhaeswim.module.applycompetition.repository;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyCompetitionEventRepository extends JpaRepository<ApplyCompetitionEvent, Long> {

    Optional<ApplyCompetitionEvent> findByUserAndCompetitionEvent(User user, CompetitionEvent competitionEvent);

    List<ApplyCompetitionEvent> findByUser(User user);
}
