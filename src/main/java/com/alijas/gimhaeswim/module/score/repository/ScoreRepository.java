package com.alijas.gimhaeswim.module.score.repository;

import com.alijas.gimhaeswim.module.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
