package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.Referee;
import com.alijas.gimhaeswim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefereeRepository extends JpaRepository<Referee, Integer> {
    @Query(value="select * from referee where referee_name = ?1 and password = ?2" ,nativeQuery=true)
    Referee getReferee(String name, String pwd);
}
