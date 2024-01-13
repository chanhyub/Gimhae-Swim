package com.alijas.gimhaeswim.module.applycompetition.service;

import com.alijas.gimhaeswim.module.applycompetition.dto.ApplyCompetitionManagementIndividualDTO;
import com.alijas.gimhaeswim.module.applycompetition.dto.ApplyCompetitionManagementOrganizationDTO;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetition;
import com.alijas.gimhaeswim.module.applycompetition.entity.ApplyCompetitionEvent;
import com.alijas.gimhaeswim.module.applycompetition.enums.DepositStatus;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionEventRepository;
import com.alijas.gimhaeswim.module.applycompetition.repository.ApplyCompetitionRepository;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.team.entity.Team;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswim.module.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplyCompetitionService {

    private final ApplyCompetitionRepository applyCompetitionRepository;

    private final ApplyCompetitionEventRepository applyCompetitionEventRepository;

    private final TeamMemberRepository teamMemberRepository;

    public ApplyCompetitionService(ApplyCompetitionRepository applyCompetitionRepository, ApplyCompetitionEventRepository applyCompetitionEventRepository, TeamMemberRepository teamMemberRepository) {
        this.applyCompetitionRepository = applyCompetitionRepository;
        this.applyCompetitionEventRepository = applyCompetitionEventRepository;
        this.teamMemberRepository = teamMemberRepository;
    }


    @Transactional
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

    @Transactional
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

    public Page<ApplyCompetitionManagementIndividualDTO> findAllByApplyStatusAndUserNotNull(Pageable pageable) {
        Page<ApplyCompetition> applyCompetitionPage = applyCompetitionRepository.findAllByApplyStatusAndUserNotNull(pageable, ApplyStatus.WAITING);

        List<ApplyCompetitionManagementIndividualDTO> applyCompetitionManagementIndividualDTOPage = new ArrayList<>();

        applyCompetitionPage.forEach(
                applyCompetition -> {
                    ApplyCompetitionManagementIndividualDTO applyCompetitionManagementIndividualDTO = applyCompetition.toApplyCompetitionManagementIndividualDTO();
                    List<ApplyCompetitionEvent> applyCompetitionEventList = applyCompetitionEventRepository.findByApplyCompetition(applyCompetition);
                    List<String> competitionEventDepartmentList = new ArrayList<>();

                    applyCompetitionEventList.forEach(
                            applyCompetitionEvent -> {
                                competitionEventDepartmentList.add(
                                        (applyCompetitionEvent.getCompetitionEvent().getEventType().name().split("_")[1].equals("MALE") ? "남자" : applyCompetitionEvent.getCompetitionEvent().getEventType().name().split("_")[1].equals("FEMALE") ? "여자" : "혼성") + ' ' +
                                        applyCompetitionEvent.getCompetitionEvent().getDepartment().getDepartmentName() + ' ' +
                                                applyCompetitionEvent.getCompetitionEvent().getEvent().getEventName() + ' ' +
                                                applyCompetitionEvent.getCompetitionEvent().getMeter().getMeter() + ' '
                                );
                            }
                    );
                    applyCompetitionManagementIndividualDTO.setDepartmentList(competitionEventDepartmentList);

                    applyCompetitionManagementIndividualDTOPage.add(applyCompetitionManagementIndividualDTO);
                }
        );

        return new PageImpl<>(applyCompetitionManagementIndividualDTOPage, pageable, applyCompetitionPage.getTotalElements());
    }

    public Page<ApplyCompetitionManagementOrganizationDTO> findAllByApplyStatusAndTeamNotNull(Pageable pageable) {
        Page<ApplyCompetition> applyCompetitionPage = applyCompetitionRepository.findAllByApplyStatusAndTeamNotNull(pageable, ApplyStatus.WAITING);

        List<ApplyCompetitionManagementOrganizationDTO> applyCompetitionManagementOrganizationDTOPage = new ArrayList<>();


        applyCompetitionPage.forEach(
                applyCompetition -> {
                    TeamMember teamMember = teamMemberRepository.findByTeamAndPosition(applyCompetition.getTeam(), TeamMemberPosition.LEADER);

                    ApplyCompetitionManagementOrganizationDTO applyCompetitionManagementOrganizationDTO = applyCompetition.toApplyCompetitionManagementOrganizationDTO(teamMember);
                    List<ApplyCompetitionEvent> applyCompetitionEventList = applyCompetitionEventRepository.findByApplyCompetition(applyCompetition);
                    List<String> competitionEventDepartmentList = new ArrayList<>();

                    applyCompetitionEventList.forEach(
                            applyCompetitionEvent -> {
                                competitionEventDepartmentList.add(
                                        (applyCompetitionEvent.getCompetitionEvent().getEventType().name().split("_")[1].equals("MALE") ? "남자" : applyCompetitionEvent.getCompetitionEvent().getEventType().name().split("_")[1].equals("FEMALE") ? "여자" : "혼성") + ' ' +
                                                applyCompetitionEvent.getCompetitionEvent().getDepartment().getDepartmentName() + ' ' +
                                                applyCompetitionEvent.getCompetitionEvent().getEvent().getEventName() + ' ' +
                                                applyCompetitionEvent.getCompetitionEvent().getMeter().getMeter() + ' '
                                );
                            }
                    );
                    applyCompetitionManagementOrganizationDTO.setDepartmentList(competitionEventDepartmentList);

                    applyCompetitionManagementOrganizationDTOPage.add(applyCompetitionManagementOrganizationDTO);
                }
        );
        return new PageImpl<>(applyCompetitionManagementOrganizationDTOPage, pageable, applyCompetitionPage.getTotalElements());
    }

    public void apply(ApplyCompetition applyCompetition) {
        applyCompetition.setApplyStatus(ApplyStatus.APPROVED);
        applyCompetition.setDepositStatus(DepositStatus.DEPOSITED);
        applyCompetitionRepository.save(applyCompetition);
    }
}
