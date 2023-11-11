package com.alijas.gimhaeswim.module.team.repository;

import com.alijas.gimhaeswim.module.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
