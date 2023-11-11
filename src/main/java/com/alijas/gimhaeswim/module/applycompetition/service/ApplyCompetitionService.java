package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
    }
}
