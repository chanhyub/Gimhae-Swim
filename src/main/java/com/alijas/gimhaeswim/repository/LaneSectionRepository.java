package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.ApplicationDetail;
import com.alijas.gimhaeswim.domain.LaneSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LaneSectionRepository extends JpaRepository<LaneSection, Integer> {
    @Query(value="select * from lane_section where competition_event_seq = ?1" ,nativeQuery=true)
    List<LaneSection> getLaneSectionList(Integer competitionEventSeq);
}
