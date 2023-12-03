package com.alijas.gimhaeswim.module.competition.service;

import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    public Page<CompetitionListDTO> findAll(Pageable pageable) {
        Page<Competition> page = competitionRepository.findAllByStatus(pageable, CompetitionStatus.ACTIVE);
        return page.map(Competition::toCompetitionListDTO);
    }

    public Optional<Competition> getCompetition(Long id) {
        return competitionRepository.findById(id);
    }
}
