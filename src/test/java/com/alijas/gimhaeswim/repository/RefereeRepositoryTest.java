package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("RefereeRepositoryTest 테스트")
public class RefereeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RefereeRepository refereeRepository;
}
