package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.enums.EventType;
import com.alijas.gimhaeswim.module.competition.enums.MoreOrLess;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.enums.status.DepartmentStatus;
import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionEventRepository;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("CompetitionEventRepository 테스트")
public class CompetitionEventRepositoryTest {

    @Autowired
    private CompetitionEventRepository competitionEventRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void init() {
        Competition competition = new Competition();
        setUp(
            "ORGANIZATION_MALE",
            getDepartment(10, "남자학생부"),
            getEvent("테스트용"),
            getMeter("50M"),
            100,
            10,
            8,
            6,
            4,
            2,
            0,
            0,
            0,
            getCompetition(
                "2021년 제1회 김해시장배 수영대회",
                "2021-10-28 09:00:00",
                "김해시 체육관",
                "2021-10-01 00:00:00",
                "2021-10-20 23:59:59",
                "2021년 제1회 김해시장배 수영대회",
                10000,
                5000,
                "신한은행 110-123-456789 홍길동"
            )
        );
    }

    @Test
    @DisplayName("대회 종목 전체 조회")
    void selectAll() {
        List<CompetitionEvent> competitionEventList = competitionEventRepository.findAll();
        assertNotEquals(competitionEventList.size(), 0);

        assertEquals(competitionEventList.get(0).getEventType(), EventType.ORGANIZATION_MALE);
        assertEquals(competitionEventList.get(0).getDepartment().getDepartmentAge(), 10);
        assertEquals(competitionEventList.get(0).getDepartment().getDepartmentInfo(), "여자학생부");
        assertEquals(competitionEventList.get(0).getEvent().getEventName(), "접영");
        assertEquals(competitionEventList.get(0).getMeter().getMeter(), "100M");
        assertEquals(competitionEventList.get(0).getEventCapacity(), 100);
        assertEquals(competitionEventList.get(0).getFirstScore(), 10);
        assertEquals(competitionEventList.get(0).getSecondScore(), 8);
        assertEquals(competitionEventList.get(0).getThirdScore(), 6);
        assertEquals(competitionEventList.get(0).getFourthScore(), 4);
        assertEquals(competitionEventList.get(0).getFifthScore(), 2);
        assertEquals(competitionEventList.get(0).getSixthScore(), 0);
        assertEquals(competitionEventList.get(0).getSeventhScore(), 0);
        assertEquals(competitionEventList.get(0).getEighthScore(), 0);
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionName(), "2021년 제1회 김해시장배 수영대회");
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"));
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionPlace(), "김해시 체육관");
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"));
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"));
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionContent(), "2021년 제1회 김해시장배 수영대회");
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionFee(), 10000);
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionStudentFee(), 5000);
        assertEquals(competitionEventList.get(0).getCompetition().getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
        assertEquals(competitionEventList.get(0).getCompetition().getStatus(), CompetitionStatus.ACTIVE);
    }

    @Test
    @DisplayName("대회 종목 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<CompetitionEvent> optionalCompetitionEvent = competitionEventRepository.findById(1L);

        if (optionalCompetitionEvent.isPresent()) {
            CompetitionEvent result = optionalCompetitionEvent.get();
            assertEquals(result.getEventType(), EventType.ORGANIZATION_MALE);
            assertEquals(result.getDepartment().getDepartmentAge(), 10);
            assertEquals(result.getDepartment().getDepartmentInfo(), "여자학생부");
            assertEquals(result.getEvent().getEventName(), "접영");
            assertEquals(result.getMeter().getMeter(), "100M");
            assertEquals(result.getEventCapacity(), 100);
            assertEquals(result.getFirstScore(), 10);
            assertEquals(result.getSecondScore(), 8);
            assertEquals(result.getThirdScore(), 6);
            assertEquals(result.getFourthScore(), 4);
            assertEquals(result.getFifthScore(), 2);
            assertEquals(result.getSixthScore(), 0);
            assertEquals(result.getSeventhScore(), 0);
            assertEquals(result.getEighthScore(), 0);
            assertNotNull(result.getCompetition());
        } else {
            assertNull(optionalCompetitionEvent);
        }
    }

    @Test
    @DisplayName("대회 종목 추가 및 삭제")
    void insertAndDelete() {
        CompetitionEvent competitionEvent = setUp(
                "ORGANIZATION_FEMALE",
                getDepartment(10, "여자학생부"),
                getEvent("테스트용1"),
                getMeter("50M"),
                100,
                10,
                8,
                6,
                4,
                2,
                0,
                0,
                0,
                getCompetition(
                        "2021년 제1회 김해시장배 수영대회",
                        "2021-10-28 09:00:00",
                        "김해시 체육관",
                        "2021-10-01 00:00:00",
                        "2021-10-20 23:59:59",
                        "2021년 제1회 김해시장배 수영대회",
                        10000,
                        5000,
                        "신한은행 110-123-456789 홍길동"
                )
        );

        Optional<CompetitionEvent> optionalCompetitionEvent = competitionEventRepository.findById(competitionEvent.getId());
        if (optionalCompetitionEvent.isPresent()) {
            CompetitionEvent result = optionalCompetitionEvent.get();
            assertEquals(result.getEventType(), EventType.ORGANIZATION_FEMALE);
            assertEquals(result.getDepartment().getDepartmentAge(), 10);
            assertEquals(result.getDepartment().getDepartmentInfo(), "여자학생부");
            assertEquals(result.getEvent().getEventName(), "테스트용1");
            assertEquals(result.getMeter().getMeter(), "50M");
            assertEquals(result.getEventCapacity(), 100);
            assertEquals(result.getFirstScore(), 10);
            assertEquals(result.getSecondScore(), 8);
            assertEquals(result.getThirdScore(), 6);
            assertEquals(result.getFourthScore(), 4);
            assertEquals(result.getFifthScore(), 2);
            assertEquals(result.getSixthScore(), 0);
            assertEquals(result.getSeventhScore(), 0);
            assertEquals(result.getEighthScore(), 0);
            assertNotNull(result.getCompetition());
        } else {
            assertNull(optionalCompetitionEvent);
        }
    }

    private CompetitionEvent setUp(
        String eventType,
        Department department,
        Event event,
        Meter meter,
        Integer eventCapacity,
        Integer firstScore,
        Integer secondScore,
        Integer thirdScore,
        Integer fourthScore,
        Integer fifthScore,
        Integer sixthScore,
        Integer seventhScore,
        Integer eighthScore,
        Competition competition
    ) {
        CompetitionEvent competitionEvent = new CompetitionEvent(
                null,
                EventType.valueOf(eventType),
                department,
                event,
                meter,
                eventCapacity,
                firstScore,
                secondScore,
                thirdScore,
                fourthScore,
                fifthScore,
                sixthScore,
                seventhScore,
                eighthScore,
                competition
        );
        return entityManager.persist(competitionEvent);
    }

    private Department getDepartment(
            Integer departmentAge,
            String departmentInfo
    ) {
        return entityManager.persist(new Department(
            null,
            departmentAge,
            MoreOrLess.LESS,
            DepartmentStatus.ACTIVE,
            departmentInfo
        ));
    }

    private Event getEvent(
            String eventName
    ) {
        return entityManager.persist(new Event(
            null,
            eventName,
            EventStatus.ACTIVE
        ));
    }

    private Meter getMeter(
            String meter
    ) {
        return entityManager.persist(new Meter(
            null,
            meter
        ));
    }

    private Competition getCompetition(
        String competitionName,
        String competitionDate,
        String competitionPlace,
        String competitionApplyStartDate,
        String competitionApplyEndDate,
        String competitionContent,
        Integer competitionFee,
        Integer competitionStudentFee,
        String competitionAccount
    ) {
        return entityManager.persist(new Competition(
            null,
            competitionName,
            DateTimeConverter.StringToLocalDateTime(competitionDate),
            competitionPlace,
            DateTimeConverter.StringToLocalDateTime(competitionApplyStartDate),
            DateTimeConverter.StringToLocalDateTime(competitionApplyEndDate),
            competitionContent,
            competitionFee,
            competitionStudentFee,
            competitionAccount,
            CompetitionStatus.ACTIVE
        ));
    }
}
