package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.Team;
import com.alijas.gimhaeswim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(value="select * from team where team_name = ?1" ,nativeQuery=true)
    Team getTeam(String teamName);
}
