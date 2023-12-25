package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.enums.DepositStatus;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionRepository;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
    }


    public void individualSave(Competition competition, User user) {
        applyCompetitionRepository.save(
                new ApplyCompetition(
                        null,
                        competition,
                        user,
                        null,
                        DepositStatus.NOT_DEPOSITED,
                        ApplyStatus.WAITING
                )
        );
    }

    public void organizationSave(Competition competition, Team team) {
        applyCompetitionRepository.save(
                new ApplyCompetition(
                        null,
                        competition,
                        null,
                        team,
                        DepositStatus.NOT_DEPOSITED,
                        ApplyStatus.WAITING
                )
        );
    }
}
