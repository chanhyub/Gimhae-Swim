package com.alijas.gimhaeswim.module.team.repository;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeam(Team team);
}
