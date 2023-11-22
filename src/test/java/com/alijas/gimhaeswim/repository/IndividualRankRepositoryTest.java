package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.enums.RoleType;
import com.alijas.gimhaeswim.module.competition.entity.*;
import com.alijas.gimhaeswim.module.competition.enums.EventType;
import com.alijas.gimhaeswim.module.competition.enums.MoreOrLess;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.module.competition.enums.status.DepartmentStatus;
import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswim.module.rank.entity.IndividualRank;
import com.alijas.gimhaeswim.module.rank.repository.IndividualRankRepository;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.Gender;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
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
@DisplayName("IndividualRankRepositoryTest 테스트")
public class IndividualRankRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IndividualRankRepository individualRankRepository;

    @BeforeEach
    public void init() {
        setUp(
                1
        );
    }

    @Test
    @DisplayName("개인기록 전체 조회")
    void selectAll() {
        var individualRanks = individualRankRepository.findAll();
        assertNotEquals(individualRanks.size(), 0);

        assertEquals(individualRanks.get(0).getCompetitionEvent().getEventType(), EventType.ORGANIZATION_MALE);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getDepartment().getDepartmentAge(), 20);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getDepartment().getMoreOrLess(), MoreOrLess.LESS);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getDepartment().getStatus(), DepartmentStatus.ACTIVE);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getDepartment().getDepartmentInfo(), "남자학생부");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getEvent().getEventName(), "테스트 종목");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getEvent().getStatus(), EventStatus.ACTIVE);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getMeter().getMeter(), "3500m");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getEventCapacity(), 200);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getFirstScore(), 10);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getSecondScore(), 9);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getThirdScore(), 8);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getFourthScore(), 7);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getFifthScore(), 6);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getSixthScore(), 5);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getSeventhScore(), 4);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getEighthScore(), 3);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionName(), "테스트 대회");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionPlace(), "시청");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionContent(), "내용");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionFee(), 30000);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionStudentFee(), 20000);
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
        assertEquals(individualRanks.get(0).getCompetitionEvent().getCompetition().getStatus(), CompetitionStatus.ACTIVE);
        assertEquals(individualRanks.get(0).getRank(), 1);
        assertEquals(individualRanks.get(0).getUser().getUsername(), "Big Jorge");
        assertEquals(individualRanks.get(0).getUser().getPassword(), "1234");
        assertEquals(individualRanks.get(0).getUser().getBirthday(), "1999-10-28");
        assertEquals(individualRanks.get(0).getUser().getPhoneNumber(), "01083384583");
        assertEquals(individualRanks.get(0).getUser().getEmail(), "test1234@test.com");
        assertEquals(individualRanks.get(0).getUser().getGender(), Gender.M);
        assertEquals(individualRanks.get(0).getUser().getStatus(), UserStatus.ACTIVE);
        assertEquals(individualRanks.get(0).getUser().getApplyStatus(), ApplyStatus.APPROVED);
        assertEquals(individualRanks.get(0).getUser().getRole(), RoleType.USER);
    }

    @Test
    @DisplayName("개인기록 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<IndividualRank> optionalIndividualRank = individualRankRepository.findById(1L);

        if (optionalIndividualRank.isPresent()) {
            IndividualRank result = optionalIndividualRank.get();

            assertEquals(result.getCompetitionEvent().getEventType(), EventType.ORGANIZATION_MALE);
            assertEquals(result.getCompetitionEvent().getDepartment().getDepartmentAge(), 20);
            assertEquals(result.getCompetitionEvent().getDepartment().getMoreOrLess(), MoreOrLess.LESS);
            assertEquals(result.getCompetitionEvent().getDepartment().getStatus(), DepartmentStatus.ACTIVE);
            assertEquals(result.getCompetitionEvent().getDepartment().getDepartmentInfo(), "남자학생부");
            assertEquals(result.getCompetitionEvent().getEvent().getEventName(), "테스트 종목");
            assertEquals(result.getCompetitionEvent().getEvent().getStatus(), EventStatus.ACTIVE);
            assertEquals(result.getCompetitionEvent().getMeter().getMeter(), "3500m");
            assertEquals(result.getCompetitionEvent().getEventCapacity(), 200);
            assertEquals(result.getCompetitionEvent().getFirstScore(), 10);
            assertEquals(result.getCompetitionEvent().getSecondScore(), 9);
            assertEquals(result.getCompetitionEvent().getThirdScore(), 8);
            assertEquals(result.getCompetitionEvent().getFourthScore(), 7);
            assertEquals(result.getCompetitionEvent().getFifthScore(), 6);
            assertEquals(result.getCompetitionEvent().getSixthScore(), 5);
            assertEquals(result.getCompetitionEvent().getSeventhScore(), 4);
            assertEquals(result.getCompetitionEvent().getEighthScore(), 3);
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionName(), "테스트 대회");
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionPlace(), "시청");
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionContent(), "내용");
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionFee(), 30000);
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionStudentFee(), 20000);
            assertEquals(result.getCompetitionEvent().getCompetition().getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
            assertEquals(result.getCompetitionEvent().getCompetition().getStatus(), CompetitionStatus.ACTIVE);
            assertEquals(result.getRank(), 1);
            assertEquals(result.getUser().getUsername(), "Big Jorge");
            assertEquals(result.getUser().getPassword(), "1234");
            assertEquals(result.getUser().getBirthday(), "1999-10-28");
            assertEquals(result.getUser().getPhoneNumber(), "01083384583");
            assertEquals(result.getUser().getEmail(), "test1234@test.com");
            assertEquals(result.getUser().getGender(), Gender.M);
            assertEquals(result.getUser().getStatus(), UserStatus.ACTIVE);
            assertEquals(result.getUser().getApplyStatus(), ApplyStatus.APPROVED);
            assertEquals(result.getUser().getRole(), RoleType.USER);

            Integer newRank = 2;
            result.setRank(newRank);
            IndividualRank updateIndividualRank = entityManager.persist(result);

            assertEquals(updateIndividualRank.getRank(), newRank);
        } else {
            assertNotNull(optionalIndividualRank);
        }
    }

    @Test
    @DisplayName("개인기록 추가 및 삭제")
    void insertAndDelete() {
        IndividualRank setUp = insertSetUp(
                3
        );
        Optional<IndividualRank> optionalIndividualRank = individualRankRepository.findById(setUp.getId());

        if (optionalIndividualRank.isPresent()) {
            IndividualRank individualRank = optionalIndividualRank.get();
            assertEquals(individualRank.getCompetitionEvent().getEventType(), EventType.ORGANIZATION_MALE);
            assertEquals(individualRank.getCompetitionEvent().getDepartment().getDepartmentAge(), 20);
            assertEquals(individualRank.getCompetitionEvent().getDepartment().getMoreOrLess(), MoreOrLess.LESS);
            assertEquals(individualRank.getCompetitionEvent().getDepartment().getStatus(), DepartmentStatus.ACTIVE);
            assertEquals(individualRank.getCompetitionEvent().getDepartment().getDepartmentInfo(), "남자학생부");
            assertEquals(individualRank.getCompetitionEvent().getEvent().getEventName(), "테스트 종목2");
            assertEquals(individualRank.getCompetitionEvent().getEvent().getStatus(), EventStatus.ACTIVE);
            assertEquals(individualRank.getCompetitionEvent().getMeter().getMeter(), "3500m");
            assertEquals(individualRank.getCompetitionEvent().getEventCapacity(), 200);
            assertEquals(individualRank.getCompetitionEvent().getFirstScore(), 10);
            assertEquals(individualRank.getCompetitionEvent().getSecondScore(), 9);
            assertEquals(individualRank.getCompetitionEvent().getThirdScore(), 8);
            assertEquals(individualRank.getCompetitionEvent().getFourthScore(), 7);
            assertEquals(individualRank.getCompetitionEvent().getFifthScore(), 6);
            assertEquals(individualRank.getCompetitionEvent().getSixthScore(), 5);
            assertEquals(individualRank.getCompetitionEvent().getSeventhScore(), 4);
            assertEquals(individualRank.getCompetitionEvent().getEighthScore(), 3);
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionName(), "테스트 대회");
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionPlace(), "시청");
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionApplyStartDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionApplyEndDate(), DateTimeConverter.StringToLocalDateTime("2023-11-22 00:00:00"));
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionContent(), "내용");
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionFee(), 30000);
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionStudentFee(), 20000);
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getCompetitionAccount(), "신한은행 110-123-456789 홍길동");
            assertEquals(individualRank.getCompetitionEvent().getCompetition().getStatus(), CompetitionStatus.ACTIVE);
            assertEquals(individualRank.getRank(), 3);
            assertEquals(individualRank.getUser().getUsername(), "Giant Jorge");
            assertEquals(individualRank.getUser().getPassword(), "1234");
            assertEquals(individualRank.getUser().getBirthday(), "1999-10-28");
            assertEquals(individualRank.getUser().getPhoneNumber(), "01083384583");
            assertEquals(individualRank.getUser().getEmail(), "test1234@test.com");
            assertEquals(individualRank.getUser().getGender(), Gender.M);
            assertEquals(individualRank.getUser().getStatus(), UserStatus.ACTIVE);
            assertEquals(individualRank.getUser().getApplyStatus(), ApplyStatus.APPROVED);
            assertEquals(individualRank.getUser().getRole(), RoleType.USER);

            entityManager.remove(individualRank);
            Optional<IndividualRank> removeIndividualRank = individualRankRepository.findById(setUp.getId());

            removeIndividualRank.ifPresent(Assertions::assertNull);
        }
    }

    public IndividualRank setUp(
            Integer rank
    ) {
        IndividualRank individualRank = new IndividualRank(
                null,
                getCompetitionEvent(
                        200,
                        10,
                        9,
                        8,
                        7,
                        6,
                        5,
                        4,
                        3
                ),
                rank,
                getUser(
                        "Big Jorge",
                        "1234",
                        "1999-10-28",
                        "01083384583",
                        "test1234@test.com",
                        true
                )
        );
        return entityManager.persist(individualRank);
    }

    public IndividualRank insertSetUp(
            Integer rank
    ) {
        IndividualRank individualRank = new IndividualRank(
                null,
                getUpdateCompetitionEvent(
                        200,
                        10,
                        9,
                        8,
                        7,
                        6,
                        5,
                        4,
                        3
                ),
                rank,
                getUser(
                        "Giant Jorge",
                        "1234",
                        "1999-10-28",
                        "01083384583",
                        "test1234@test.com",
                        true
                )
        );
        return entityManager.persist(individualRank);
    }

    private CompetitionEvent getCompetitionEvent(
            Integer eventCapacity,
            Integer firstScore,
            Integer secondScore,
            Integer thirdScore,
            Integer fourthScore,
            Integer fifthScore,
            Integer sixthScore,
            Integer seventhScore,
            Integer eighthScore
    ){
        return entityManager.persist(new CompetitionEvent(
                null,
                EventType.ORGANIZATION_MALE,
                getDepartment(
                        20,
                        "남자학생부"
                ),
                getEvent(
                        "테스트 종목"
                ),
                getMeter(
                        "3500m"
                ),
                eventCapacity,
                firstScore,
                secondScore,
                thirdScore,
                fourthScore,
                fifthScore,
                sixthScore,
                seventhScore,
                eighthScore,
                getCompetition(
                        "테스트 대회",
                        "2023-11-22 00:00:00",
                        "시청",
                        "2023-11-22 00:00:00",
                        "2023-11-22 00:00:00",
                        "내용",
                        30000,
                        20000,
                        "신한은행 110-123-456789 홍길동"
                )
        ));
    }

    private CompetitionEvent getUpdateCompetitionEvent(
            Integer eventCapacity,
            Integer firstScore,
            Integer secondScore,
            Integer thirdScore,
            Integer fourthScore,
            Integer fifthScore,
            Integer sixthScore,
            Integer seventhScore,
            Integer eighthScore
    ){
        return entityManager.persist(new CompetitionEvent(
                null,
                EventType.ORGANIZATION_MALE,
                getDepartment(
                        20,
                        "남자학생부"
                ),
                getEvent(
                        "테스트 종목2"
                ),
                getMeter(
                        "3500m"
                ),
                eventCapacity,
                firstScore,
                secondScore,
                thirdScore,
                fourthScore,
                fifthScore,
                sixthScore,
                seventhScore,
                eighthScore,
                getCompetition(
                        "테스트 대회",
                        "2023-11-22 00:00:00",
                        "시청",
                        "2023-11-22 00:00:00",
                        "2023-11-22 00:00:00",
                        "내용",
                        30000,
                        20000,
                        "신한은행 110-123-456789 홍길동"
                )
        ));
    }

    private User getUser(
            String username,
            String password,
            String birthday,
            String phoneNumber,
            String email,
            boolean isAgree
    ) {
        return entityManager.persist(new User(
                null,
                username,
                password,
                birthday,
                phoneNumber,
                email,
                Gender.M,
                isAgree,
                UserStatus.ACTIVE,
                ApplyStatus.APPROVED,
                RoleType.USER
        ));
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
