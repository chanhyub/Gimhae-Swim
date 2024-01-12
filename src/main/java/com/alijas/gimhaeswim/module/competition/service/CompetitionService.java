package com.alijas.gimhaeswim.module.competition.service;

import com.alijas.gimhaeswim.exception.CustomRestException;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.enums.EventType;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.repository.*;
import com.alijas.gimhaeswim.module.competition.request.CompetitionSaveRequest;
import com.alijas.gimhaeswim.module.competition.request.CompetitionUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    private final CompetitionEventRepository competitionEventRepository;

    private final DepartmentRepository departmentRepository;

    private final MeterRepository meterRepository;

    private final EventRepository eventRepository;

    public CompetitionService(CompetitionRepository competitionRepository, CompetitionEventRepository competitionEventRepository, DepartmentRepository departmentRepository, MeterRepository meterRepository, EventRepository eventRepository) {
        this.competitionRepository = competitionRepository;
        this.competitionEventRepository = competitionEventRepository;
        this.departmentRepository = departmentRepository;
        this.meterRepository = meterRepository;
        this.eventRepository = eventRepository;
    }

    public Page<CompetitionListDTO> findAll(Pageable pageable) {
        Page<Competition> page = competitionRepository.findAllByStatus(pageable, CompetitionStatus.ACTIVE);
        return page.map(Competition::toCompetitionListDTO);
    }

    public Optional<Competition> getCompetition(Long id) {
        return competitionRepository.findById(id);
    }

    @Transactional
    public void saveCompetitionAndCompetitionEvent(CompetitionSaveRequest competitionSaveRequest) {
        Competition competition = competitionRepository.save(competitionSaveRequest.toCompetition());

        competitionSaveRequest.competitionEventList().forEach(competitionEventSaveRequest -> {
            Optional<Department> optionalDepartment = departmentRepository.findById(competitionEventSaveRequest.getDepartment());
            if (optionalDepartment.isEmpty()) {
                throw new CustomRestException("존재하지 않는 부 입니다.", HttpStatus.BAD_REQUEST);
            }

            Optional<Meter> optionalMeter = meterRepository.findById(competitionEventSaveRequest.getMeter());
            if (optionalMeter.isEmpty()) {
                throw new CustomRestException("존재하지 않는 미터 입니다.", HttpStatus.BAD_REQUEST);
            }

            Optional<Event> optionalEvent = eventRepository.findById(competitionEventSaveRequest.getEventName());
            if (optionalEvent.isEmpty()) {
                throw new CustomRestException("존재하지 않는 종목 입니다.", HttpStatus.BAD_REQUEST);
            }

            EventType eventType = EventType.valueOf(competitionEventSaveRequest.getEventType());

            CompetitionEvent competitionEvent = competitionEventSaveRequest.toCompetitionEvent(optionalDepartment.get(), optionalEvent.get(), optionalMeter.get(), competition, eventType);
            competitionEventRepository.save(competitionEvent);
        });
    }

    @Transactional
    public void updateCompetitionAndCompetitionEvent(CompetitionUpdateRequest competitionUpdateRequest) {
        Competition competition = competitionRepository.save(competitionUpdateRequest.toCompetition());

        competitionUpdateRequest.competitionEventList().forEach(competitionEventUpdateRequest -> {
            Optional<CompetitionEvent> optionalCompetitionEvent = competitionEventRepository.findById(competitionEventUpdateRequest.getCompetitionEventId());
            if (optionalCompetitionEvent.isEmpty()) {
                throw new CustomRestException("존재하지 않는 대회 종목 입니다.", HttpStatus.BAD_REQUEST);
            }
            CompetitionEvent competitionEvent = optionalCompetitionEvent.get();

            Optional<Department> optionalDepartment = departmentRepository.findById(competitionEventUpdateRequest.getDepartment());
            if (optionalDepartment.isEmpty()) {
                throw new CustomRestException("존재하지 않는 부 입니다.", HttpStatus.BAD_REQUEST);
            }

            Optional<Meter> optionalMeter = meterRepository.findById(competitionEventUpdateRequest.getMeter());
            if (optionalMeter.isEmpty()) {
                throw new CustomRestException("존재하지 않는 미터 입니다.", HttpStatus.BAD_REQUEST);
            }

            Optional<Event> optionalEvent = eventRepository.findById(competitionEventUpdateRequest.getEventName());
            if (optionalEvent.isEmpty()) {
                throw new CustomRestException("존재하지 않는 종목 입니다.", HttpStatus.BAD_REQUEST);
            }

            EventType eventType = EventType.valueOf(competitionEventUpdateRequest.getEventType());
            competitionEventRepository.save(
                    competitionEventUpdateRequest.toCompetitionEvent(
                            competitionEvent,
                            optionalDepartment.get(),
                            optionalEvent.get(),
                            optionalMeter.get(),
                            competition,
                            eventType
                    )
            );
        });

        if (competitionUpdateRequest.deleteCompetitionEventIds() != null && !competitionUpdateRequest.deleteCompetitionEventIds().isEmpty()) {
            String[] ids = competitionUpdateRequest.deleteCompetitionEventIds().split(",");
            List<String> list = Arrays.stream(ids).toList();
            list.forEach(id -> {
                competitionEventRepository.deleteById(Long.parseLong(id));
            });
        }

    }
}
