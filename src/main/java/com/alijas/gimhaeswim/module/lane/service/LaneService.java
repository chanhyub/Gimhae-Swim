package com.alijas.gimhaeswim.module.lane.service;

import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.lane.repository.LaneRepository;
import com.alijas.gimhaeswim.module.section.entity.Section;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaneService {

    private final LaneRepository laneRepository;

    public LaneService(LaneRepository laneRepository) {
        this.laneRepository = laneRepository;
    }

    public List<Lane> findBySection(Section section) {
        return laneRepository.findBySection(section);
    }
}
