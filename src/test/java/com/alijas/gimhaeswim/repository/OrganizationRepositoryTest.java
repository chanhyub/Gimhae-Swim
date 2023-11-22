package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.organization.entity.Organization;
import com.alijas.gimhaeswim.module.organization.enums.OrganizationStatus;
import com.alijas.gimhaeswim.module.organization.repository.OrganizationRepository;
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
@DisplayName("OrganizationRepositoryTest 테스트")
public class OrganizationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository organizationRepository;

    @BeforeEach
    public void init() {
        setUp(
                1,
                "회장",
                "빅조지",
                "무직",
                "잘생김"
        );
    }

    @Test
    @DisplayName("조직도 전체 조회")
    void selectAll() {
        var organizations = organizationRepository.findAll();
        assertNotEquals(organizations.size(), 0);

        assertEquals(organizations.get(0).getOrganizationIndex(), 1);
        assertEquals(organizations.get(0).getPosition(), "회장");
        assertEquals(organizations.get(0).getName(), "빅조지");
        assertEquals(organizations.get(0).getJob(), "무직");
        assertEquals(organizations.get(0).getNote(), "잘생김");
        assertEquals(organizations.get(0).getStatus(), OrganizationStatus.ACTIVE);
    }

    @Test
    @DisplayName("조직도 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Organization> optionalOrganization = organizationRepository.findById(1L);

        if (optionalOrganization.isPresent()) {
            Organization result = optionalOrganization.get();

            assertEquals(result.getOrganizationIndex(), 1);
            assertEquals(result.getPosition(), "회장");
            assertEquals(result.getName(), "빅조지");
            assertEquals(result.getJob(), "무직");
            assertEquals(result.getNote(), "잘생김");
            assertEquals(result.getStatus(), OrganizationStatus.ACTIVE);

            String newNote = "진짜 잘생김";
            result.setNote(newNote);
            Organization updateOrganization = entityManager.persist(result);

            assertEquals(updateOrganization.getNote(), newNote);
        } else {
            assertNotNull(optionalOrganization);
        }
    }

    @Test
    @DisplayName("조직도 추가 및 삭제")
    void insertAndDelete() {
        Organization setUp = setUp(
                2,
                "부회장",
                "데릭",
                "개발자",
                "조지보단 조금 덜 잘생김"
        );
        Optional<Organization> optionalOrganization = organizationRepository.findById(setUp.getId());

        if (optionalOrganization.isPresent()) {
            Organization organization = optionalOrganization.get();
            assertEquals(organization.getOrganizationIndex(), 2);
            assertEquals(organization.getPosition(), "부회장");
            assertEquals(organization.getName(), "데릭");
            assertEquals(organization.getJob(), "개발자");
            assertEquals(organization.getNote(), "조지보단 조금 덜 잘생김");
            assertEquals(organization.getStatus(), OrganizationStatus.ACTIVE);

            entityManager.remove(organization);
            Optional<Organization> removeOrganization = organizationRepository.findById(setUp.getId());

            removeOrganization.ifPresent(Assertions::assertNull);
        }
    }

    public Organization setUp(
            Integer organizationIndex,
            String position,
            String name,
            String job,
            String note
    ) {
        Organization organization = new Organization(
                null,
                organizationIndex,
                position,
                name,
                job,
                note,
                OrganizationStatus.ACTIVE
        );
        return entityManager.persist(organization);
    }
}
