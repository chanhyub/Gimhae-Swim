package com.alijas.gimhaeswim.module.rank.repository;

import org.apache.poi.ss.formula.functions.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {
}
