package com.alijas.gimhaeswim.module.applycompetition.repository;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyCompetitionRepository extends JpaRepository<ApplyCompetition, Long> {
    List<ApplyCompetition> findByUser(User user);

    List<ApplyCompetition> findByUserOrTeam(User user, Team team);

    List<ApplyCompetition> findByTeam(Team team);
}
