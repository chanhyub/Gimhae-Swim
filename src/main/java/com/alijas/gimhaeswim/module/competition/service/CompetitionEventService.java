package com.alijas.gimhaeswim.module.competition.service;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionEventListApplyDTO;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionEventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<CompetitionEvent> getCompetitionEventByCompetitionIdAndType(Long competitionId, String type) {
        return competitionEventRepository.findByCompetitionId(competitionId)
                .stream()
                .filter(competitionEvent ->
                        competitionEvent.getEventType().name().split("_")[0].equals(type))
                .toList();
    }

    public List<CompetitionEvent> getCompetitionEventByCompetitionId(Long id, String type) {

        return competitionEventRepository.findByCompetitionId(id)
                .stream()
                .filter(competitionEvent -> competitionEvent.getEventType().name().split("_")[0].equals(type))
                .toList();
    }

    public List<CompetitionEvent> getCompetitionEventByCompetition(Long id) {
        return competitionEventRepository.findByCompetitionId(id);
    }

    public Integer getCompetitionEventCount(Long id) {
        return competitionEventRepository.findByCompetitionId(id).size();
    }

    @Transactional
    public void deleteCompetitionEvent(String s) {
        String[] ids = s.split(",");
        for (int i = 0; i < ids.length - 1; i++) {
            competitionEventRepository.deleteById(Long.parseLong(ids[i]));
        }
    }
}
