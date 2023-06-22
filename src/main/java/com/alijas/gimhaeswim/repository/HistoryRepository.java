package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query(value="select * from history order by history_year desc, history_month asc" ,nativeQuery=true)
    List<History> getHistoryList();
}
