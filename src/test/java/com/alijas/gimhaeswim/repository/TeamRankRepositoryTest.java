package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.rank.repository.TeamRankRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("TeamRankRepositoryTest 테스트")
public class TeamRankRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRankRepository teamRankRepository;
}
