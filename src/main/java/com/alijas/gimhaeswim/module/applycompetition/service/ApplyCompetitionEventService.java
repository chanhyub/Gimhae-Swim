package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionEventRepository;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyCompetitionEventService {

    private final ApplyCompetitionEventRepository applyCompetitionEventRepository;

    public ApplyCompetitionEventService(ApplyCompetitionEventRepository applyCompetitionRepository) {
        this.applyCompetitionEventRepository = applyCompetitionRepository;
    }

    @Transactional
    public void individualSave(List<CompetitionEvent> individualCompetitionEvents, User user) {
        individualCompetitionEvents.forEach(individualCompetitionEvent -> {
            applyCompetitionEventRepository.save(new ApplyCompetitionEvent(
                    null,
                    individualCompetitionEvent,
                    user,
                    null,
                    ApplyStatus.WAITING
            ));
        });
    }

    @Transactional
    public void organizationSave(List<CompetitionEvent> organizationCompetitionEvents, User user, Team team) {
        organizationCompetitionEvents.forEach(organizationCompetitionEvent -> {
            applyCompetitionEventRepository.save(new ApplyCompetitionEvent(
                    null,
                    organizationCompetitionEvent,
                    user,
                    team,
                    ApplyStatus.WAITING
            ));
        });
    }

    public Optional<ApplyCompetitionEvent> getApplyCompetitionByUserAndCompetitionEvent(User user, CompetitionEvent competitionEvent) {
        return applyCompetitionEventRepository.findByUserAndCompetitionEvent(user, competitionEvent);
    }

    public List<ApplyCompetitionEvent> getApplyCompetitionByUser(User user) {
        return applyCompetitionEventRepository.findByUser(user);
    }
}
