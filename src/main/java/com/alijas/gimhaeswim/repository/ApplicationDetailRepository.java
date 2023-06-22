package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.ApplicationDetail;
import com.alijas.gimhaeswim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationDetailRepository extends JpaRepository<ApplicationDetail, Integer> {
    @Query(value="select * from application_detail where user_seq = ?1" ,nativeQuery=true)
    List<ApplicationDetail> getApplicationDetailFromUser(Integer userSeq);

    @Query(value="select * from application_detail where competition_seq = ?1 and is_deposit='Y'" ,nativeQuery=true)
    List<ApplicationDetail> getApplicationDetailFromCompetition(Integer competitionSeq);

    @Query(value="select * from application_detail where user_seq = ?1 and competition_seq = ?2" ,nativeQuery=true)
    List<ApplicationDetail> getApplicationDetailFromUserAndCompetition(Integer userSeq, Integer competitionSeq);

    @Query(value="select * from application_detail where team_seq = ?1 and competition_seq = ?2" ,nativeQuery=true)
    List<ApplicationDetail> getApplicationDetailFromTeamAndCompetition(Integer teamSeq, Integer competitionSeq);
}
