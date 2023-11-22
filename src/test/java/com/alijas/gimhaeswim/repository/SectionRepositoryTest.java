package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.section.repository.SectionRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("SectionRepositoryTest 테스트")
public class SectionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SectionRepository sectionRepository;
}
