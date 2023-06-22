package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.TeamPoint;
import com.alijas.gimhaeswim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamPointRepository extends JpaRepository<TeamPoint, Integer> {
    @Query(value="select * from team_point where competition_seq = ?1" ,nativeQuery=true)
    List<TeamPoint> getTeamPoint(Integer competitionSeq);

    @Query(value="select * from user where is_done = 'Y'" ,nativeQuery=true)
    List<User> getUserListIsDone();

    @Query(value="select * from user where user_name = ?1 and password = ?2 and is_done = 'Y'" ,nativeQuery=true)
    User getUser(String userName, String pwd);
}
