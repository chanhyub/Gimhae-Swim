package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionRepository;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
    }

    @Transactional
    public void save(List<CompetitionEvent> individualCompetitionEvents, List<CompetitionEvent> organizationCompetitionEvents, User user, Team team) {
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

        organizationCompetitionEvents.forEach(organizationCompetitionEvent -> {
            applyCompetitionRepository.save(new ApplyCompetition(
                    null,
                    organizationCompetitionEvent,
                    null,
                    team,
                    ApplyStatus.WAITING
            ));
        });
    }
}
