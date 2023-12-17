package com.alijas.gimhaeswim.module.team.repository;

import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeam(Team team);

    Optional<TeamMember> findByUser(User user);
}
