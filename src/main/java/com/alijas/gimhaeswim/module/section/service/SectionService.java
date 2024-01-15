package com.alijas.gimhaeswim.module.section.service;

import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionEventRepository;
import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.lane.repository.LaneRepository;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import com.alijas.gimhaeswim.module.section.dto.SectionDTO;
import com.alijas.gimhaeswim.module.section.entity.Section;
import com.alijas.gimhaeswim.module.section.repository.SectionRepository;
import com.alijas.gimhaeswim.module.section.request.SectionSaveRequest;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    private final CompetitionEventRepository competitionEventRepository;

    private final LaneRepository laneRepository;

    private final UserRepository userRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final RefereeRepository refereeRepository;

    public SectionService(SectionRepository sectionRepository, CompetitionEventRepository competitionEventRepository, LaneRepository laneRepository, UserRepository userRepository, TeamMemberRepository teamMemberRepository, RefereeRepository refereeRepository) {
        this.sectionRepository = sectionRepository;
        this.competitionEventRepository = competitionEventRepository;
        this.laneRepository = laneRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.refereeRepository = refereeRepository;
    }

    @Transactional
    public void save(SectionSaveRequest sectionSaveRequest) {
        List<SectionDTO> saveRequest = sectionSaveRequest.getSaveRequest();
        saveRequest.forEach(sectionDTO -> {
            if (sectionDTO.getSectionId() == null) {
                /* 신규 저장 */
                List<Section> byCompetitionEventId = sectionRepository.findByCompetitionEventId(sectionSaveRequest.getCompetitionEventId());
                Optional<CompetitionEvent> optionalCompetitionEvent = competitionEventRepository.findById(sectionSaveRequest.getCompetitionEventId());
                if (optionalCompetitionEvent.isEmpty()) {
                    throw new CustomRestException("대회 종목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                }
                Section saveSection = sectionRepository.save(
                        new Section(
                                null,
                                byCompetitionEventId.size() + 1,
                                optionalCompetitionEvent.get()
                        )
                );
                sectionDTO.getSectionData().forEach(laneDTO -> {
                    if (laneDTO.getUserId() == null) {
                        /* 신규 저장 - 팀 경기  */
                        if (laneDTO.getTeamMemberId() == null) {
                            laneRepository.save(new Lane(null, laneDTO.getLaneNumber(), null, null, null, saveSection));
                        } else {
                            Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(laneDTO.getTeamMemberId());
                            if (optionalTeamMember.isEmpty()) {
                                throw new CustomRestException("팀원이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                            }
                            if (laneDTO.getRefereeId() == null) {
                                throw new CustomRestException("심판을 선택해주세요", HttpStatus.BAD_REQUEST);
                            } else {
                                Optional<Referee> optionalReferee = refereeRepository.findById(laneDTO.getRefereeId());
                                if (optionalReferee.isEmpty()) {
                                    throw new CustomRestException("심판이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                                }
                                laneRepository.save(
                                        laneDTO.toEntity(optionalTeamMember.get(), optionalReferee.get(), saveSection)
                                );
                            }
                        }
                    } else {
                        /* 신규 저장 - 개인 경기 */
                        Optional<User> optionalUser = userRepository.findById(laneDTO.getUserId());
                        if (optionalUser.isEmpty()) {
                            throw new CustomRestException("개인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                        }
                        if (laneDTO.getRefereeId() == null) {
                            throw new CustomRestException("심판을 선택해주세요", HttpStatus.BAD_REQUEST);
                        } else {
                            Optional<Referee> optionalReferee = refereeRepository.findById(laneDTO.getRefereeId());
                            if (optionalReferee.isEmpty()) {
                                throw new CustomRestException("심판이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                            }
                            laneRepository.save(
                                    laneDTO.toEntity(optionalUser.get(), optionalReferee.get(), saveSection)
                            );
                        }
                    }
                });
            } else {
                /* 수정 */
                Optional<Section> optionalSection = sectionRepository.findById(sectionDTO.getSectionId());
                if (optionalSection.isEmpty()) {
                    throw new CustomRestException("조가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                }
                Section section = optionalSection.get();

                sectionDTO.getSectionData().forEach(laneDTO -> {
                    Optional<Lane> optionalLane = laneRepository.findById(laneDTO.getLaneId());
                    if (optionalLane.isEmpty()) {
                        throw new CustomRestException("레인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                    }
                    Lane lane = optionalLane.get();
                    if (laneDTO.getUserId() == null) {
                        /* 수정 - 팀 경기 */
                        if (laneDTO.getTeamMemberId() == null) {
                            lane.setTeamMember(null);
                            lane.setReferee(null);
                            laneRepository.save(lane);
                        } else {
                            Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(laneDTO.getTeamMemberId());
                            if (optionalTeamMember.isEmpty()) {
                                throw new CustomRestException("팀원이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                            }
                            if (laneDTO.getRefereeId() == null) {
                                throw new CustomRestException("심판을 선택해주세요", HttpStatus.BAD_REQUEST);
                            } else {
                                Optional<Referee> optionalReferee = refereeRepository.findById(laneDTO.getRefereeId());
                                if (optionalReferee.isEmpty()) {
                                    throw new CustomRestException("심판이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                                }
                                lane.setTeamMember(optionalTeamMember.get());
                                lane.setReferee(optionalReferee.get());
                                laneRepository.save(lane);
                            }
                        }
                    } else {
                        /* 수정 - 개인 경기 */
                        Optional<User> optionalUser = userRepository.findById(laneDTO.getUserId());
                        if (optionalUser.isEmpty()) {
                            throw new CustomRestException("개인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                        }
                        if(laneDTO.getRefereeId() == null) {
                            throw new CustomRestException("심판을 선택해주세요", HttpStatus.BAD_REQUEST);
                        } else {
                            Optional<Referee> optionalReferee = refereeRepository.findById(laneDTO.getRefereeId());
                            if (optionalReferee.isEmpty()) {
                                throw new CustomRestException("심판이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
                            }
                            lane.setUser(optionalUser.get());
                            lane.setReferee(optionalReferee.get());
                            laneRepository.save(lane);
                        }
                    }
                });
            }
        });
    }

    public List<Section> getSectionList(Long competitionEventId) {
        return sectionRepository.findByCompetitionEventId(competitionEventId);
    }
}
