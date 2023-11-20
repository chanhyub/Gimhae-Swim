package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionRepository;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("CompetitionRepository 테스트")
public class CompetitionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetitionRepository competitionRepository;

    @BeforeEach
    public void init() {
        setUp(
                "2021년 제1회 김해시장배 수영대회",
                "2021-10-28 09:00:00",
                "김해시 체육관",
                "2021-10-01 00:00:00",
                "2021-10-20 23:59:59",
                "2021년 제1회 김해시장배 수영대회",
                10000,
                5000,
                "신한은행 110-123-456789 홍길동"
        );
    }

    @Test
    @DisplayName("대회 전체 조회")
    void selectAll() {
        var competitions = competitionRepository.findAll();
        assertNotEquals(competitions.size(), 0);

        assertEquals(competitions.get(0).getCompetitionName(), "2021년 제1회 김해시장배 수영대회");
        assertEquals(competitions.get(0).getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"));
        assertEquals(competitions.get(0).getCompetitionPlace(), "김해시 체육관");
        assertEquals(competitions.get(0).getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"));
        assertEquals(competitions.get(0).getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"));
        assertEquals(competitions.get(0).getCompetitionContent(), "2021년 제1회 김해시장배 수영대회");
        assertEquals(competitions.get(0).getCompetitionFee(), 10000);
        assertEquals(competitions.get(0).getCompetitionStudentFee(), 5000);
        assertEquals(competitions.get(0).getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
        assertEquals(competitions.get(0).getStatus(), CompetitionStatus.ACTIVE);
    }

    @Test
    @DisplayName("대회 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Competition> optionalCompetition = competitionRepository.findById(1L);

        if (optionalCompetition.isPresent()) {
            Competition result = optionalCompetition.get();

            assertEquals(result.getCompetitionName(), "2021년 제1회 김해시장배 수영대회");
            assertEquals(result.getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"));
            assertEquals(result.getCompetitionPlace(), "김해시 체육관");
            assertEquals(result.getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"));
            assertEquals(result.getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"));
            assertEquals(result.getCompetitionContent(), "2021년 제1회 김해시장배 수영대회");
            assertEquals(result.getCompetitionFee(), 10000);
            assertEquals(result.getCompetitionStudentFee(), 5000);
            assertEquals(result.getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
            assertEquals(result.getStatus(), CompetitionStatus.ACTIVE);

            String newPlace = "부산광역시 체육관";
            result.setCompetitionPlace(newPlace);
            Competition updateCompetition = entityManager.persist(result);

            assertEquals(updateCompetition.getCompetitionPlace(), newPlace);
        } else {
            assertNotNull(optionalCompetition);
        }
    }

    @Test
    @DisplayName("대회 추가 및 삭제")
    void insertAndDelete() {
        Competition setUp = setUp(
                "2021년 제2회 김해시장배 수영대회",
                "2021-10-28 09:00:00",
                "김해시 체육관",
                "2021-10-01 00:00:00",
                "2021-10-20 23:59:59",
                "2021년 제2회 김해시장배 수영대회",
                10000,
                5000,
                "신한은행 110-123-456789 홍길동"
        );
        Optional<Competition> optionalCompetition = competitionRepository.findById(setUp.getId());

        if (optionalCompetition.isPresent()) {
            Competition competition = optionalCompetition.get();
            assertEquals(competition.getCompetitionName(), "2021년 제2회 김해시장배 수영대회");
            assertEquals(competition.getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"));
            assertEquals(competition.getCompetitionPlace(), "김해시 체육관");
            assertEquals(competition.getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"));
            assertEquals(competition.getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"));
            assertEquals(competition.getCompetitionContent(), "2021년 제2회 김해시장배 수영대회");
            assertEquals(competition.getCompetitionFee(), 10000);
            assertEquals(competition.getCompetitionStudentFee(), 5000);
            assertEquals(competition.getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
            assertEquals(competition.getStatus(), CompetitionStatus.ACTIVE);

            entityManager.remove(competition);
            Optional<Competition> removeCompetition = competitionRepository.findById(setUp.getId());

            removeCompetition.ifPresent(Assertions::assertNull);
        }
    }

    public Competition setUp(
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
        Competition competition = new Competition(
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
        );
        return entityManager.persist(competition);
    }
}
