package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.competition.entity.Department;
import com.alijas.gimhaeswim.module.competition.enums.MoreOrLess;
import com.alijas.gimhaeswim.module.competition.enums.status.DepartmentStatus;
import com.alijas.gimhaeswim.module.competition.repository.DepartmentRepository;
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
@DisplayName("DepartmentRepositoryTest 테스트")
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void init() {
        setUp(
                10,
                "여자학생부"
        );
    }

    @Test
    @DisplayName("부 전체 조회")
    void selectAll() {
        var departments = departmentRepository.findAll();
        assertNotEquals(departments.size(), 0);

        assertEquals(departments.get(0).getDepartmentAge(), 10);
        assertEquals(departments.get(0).getMoreOrLess(), MoreOrLess.LESS);
        assertEquals(departments.get(0).getStatus(), DepartmentStatus.ACTIVE);
        assertEquals(departments.get(0).getDepartmentInfo(), "여자학생부");

    }

    @Test
    @DisplayName("부 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Department> optionalDepartment = departmentRepository.findById(1L);

        if (optionalDepartment.isPresent()) {
            Department result = optionalDepartment.get();

            assertEquals(result.getDepartmentAge(), 10);
            assertEquals(result.getMoreOrLess(), MoreOrLess.LESS);
            assertEquals(result.getStatus(), DepartmentStatus.ACTIVE);
            assertEquals(result.getDepartmentInfo(), "여자학생부");

            String newDepartmentInfo = "남자학생부";
            result.setDepartmentInfo(newDepartmentInfo);
            Department updateDepartment = entityManager.persist(result);

            assertEquals(updateDepartment.getDepartmentInfo(), newDepartmentInfo);
        } else {
            assertNotNull(optionalDepartment);
        }
    }

    @Test
    @DisplayName("부 추가 및 삭제")
    void insertAndDelete() {
        Department setUp = setUp(
                20,
                "여자학생부"
        );
        Optional<Department> optionalDepartment = departmentRepository.findById(setUp.getId());

        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            assertEquals(department.getDepartmentAge(), 20);
            assertEquals(department.getMoreOrLess(), MoreOrLess.MORE);
            assertEquals(department.getStatus(), DepartmentStatus.ACTIVE);
            assertEquals(department.getDepartmentInfo(), "여자학생부");

            entityManager.remove(department);
            Optional<Department> removeDepartment = departmentRepository.findById(setUp.getId());

            removeDepartment.ifPresent(Assertions::assertNull);
        }
    }

    public Department setUp(
            Integer departmentAge,
            String departmentInfo
    ) {
        Department department = new Department(
                null,
                departmentAge,
                MoreOrLess.MORE,
                DepartmentStatus.ACTIVE,
                departmentInfo
        );
        return entityManager.persist(department);
    }
}
