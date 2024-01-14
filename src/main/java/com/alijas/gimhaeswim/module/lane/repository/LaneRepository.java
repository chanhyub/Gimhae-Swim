package com.alijas.gimhaeswim.module.lane.repository;

import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaneRepository extends JpaRepository<Lane, Long> {
    List<Lane> findBySection(Section section);
}
