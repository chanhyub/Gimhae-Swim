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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
    }


    public ApplyCompetition individualSave(Competition competition, User user) {
        return applyCompetitionRepository.save(
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

    public ApplyCompetition organizationSave(Competition competition, Team team) {
        return applyCompetitionRepository.save(
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

    public Optional<ApplyCompetition> getApplyCompetition(Long id) {
        return applyCompetitionRepository.findById(id);
    }

    public List<ApplyCompetition> getApplyCompetition(User user) {
        return applyCompetitionRepository.findByUser(user);
    }

    public List<ApplyCompetition> getApplyCompetition(User user, Team team) {
        return applyCompetitionRepository.findByUserOrTeam(user, team);
    }

    public List<ApplyCompetition> getApplyCompetitionByTeam(Team team) {
        return applyCompetitionRepository.findByTeam(team);
    }

    @Transactional
    public void deleteApplyCompetition(ApplyCompetition applyCompetition) {
        applyCompetitionRepository.delete(applyCompetition);
    }
}
