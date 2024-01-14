package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
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
    public void individualSave(List<CompetitionEvent> individualCompetitionEvents, User user, ApplyCompetition applyCompetition) {
        individualCompetitionEvents.forEach(individualCompetitionEvent -> {
            applyCompetitionEventRepository.save(new ApplyCompetitionEvent(
                    null,
                    individualCompetitionEvent,
                    user,
                    null,
                    applyCompetition,
                    ApplyStatus.WAITING
            ));
        });
    }

    @Transactional
    public void organizationSave(List<CompetitionEvent> organizationCompetitionEvents, User user, Team team, ApplyCompetition applyCompetition) {
        organizationCompetitionEvents.forEach(organizationCompetitionEvent -> {
            applyCompetitionEventRepository.save(new ApplyCompetitionEvent(
                    null,
                    organizationCompetitionEvent,
                    null,
                    team,
                    applyCompetition,
                    ApplyStatus.WAITING
            ));
        });
    }

    public Optional<ApplyCompetitionEvent> getApplyCompetitionByUserAndCompetitionEvent(User user, CompetitionEvent competitionEvent) {
        return applyCompetitionEventRepository.findByUserAndCompetitionEvent(user, competitionEvent);
    }

    public Optional<ApplyCompetitionEvent> getApplyCompetitionByTeamAndCompetitionEvent(Team team, CompetitionEvent competitionEvent) {
        return applyCompetitionEventRepository.findByTeamAndCompetitionEvent(team, competitionEvent);
    }

    public List<ApplyCompetitionEvent> getApplyCompetitionByUser(User user) {
        return applyCompetitionEventRepository.findByUser(user);
    }

    public List<ApplyCompetitionEvent> getApplyCompetitionByUserOrTeam(User user, Team team) {
        return applyCompetitionEventRepository.findByUserOrTeam(user, team);
    }


    public List<ApplyCompetitionEvent> getApplyCompetitionByTeam(Team team) {
        return applyCompetitionEventRepository.findByTeam(team);
    }

    @Transactional
    public void deleteApplyCompetitionEvent(ApplyCompetition applyCompetition) {
        applyCompetitionEventRepository.deleteByApplyCompetition(applyCompetition);
    }

    public List<ApplyCompetitionEvent> getApplyCompetitionByCompetitionEvent(Long competitionEventId) {
        return applyCompetitionEventRepository.findByCompetitionEventId(competitionEventId);
    }
}
