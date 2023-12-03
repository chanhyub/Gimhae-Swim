package com.alijas.gimhaeswim.module.competition.service;

import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionEventService {

    private final CompetitionEventRepository competitionEventRepository;

    public CompetitionEventService(CompetitionEventRepository competitionEventRepository) {
        this.competitionEventRepository = competitionEventRepository;
    }

    public Optional<CompetitionEvent> getCompetitionEvent(Long id) {
        return competitionEventRepository.findById(id);
    }

    public List<CompetitionEvent> getCompetitionEventByCompetitionId(Long competitionId) {

        return competitionEventRepository.findByCompetitionId(competitionId);
    }
}
