package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionRepository;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
    }

    @Transactional
    public void individualSave(List<CompetitionEvent> individualCompetitionEvents, User user) {
        // TODO :: 로그인 되면 User 객체 넣어야 함
        individualCompetitionEvents.forEach(individualCompetitionEvent -> {
            applyCompetitionRepository.save(new ApplyCompetition(
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
            applyCompetitionRepository.save(new ApplyCompetition(
                    null,
                    organizationCompetitionEvent,
                    user,
                    team,
                    ApplyStatus.WAITING
            ));
        });
    }

    public Optional<ApplyCompetition> getApplyCompetitionByUserAndCompetitionEvent(User user, CompetitionEvent competitionEvent) {
        return applyCompetitionRepository.findByUserAndCompetitionEvent(user, competitionEvent);
    }
}
