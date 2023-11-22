package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.lane.entity.Lane;
import com.alijas.gimhaeswim.module.lane.repository.LaneRepository;
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
@DisplayName("LaneRepositoryTest 테스트")
public class LaneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LaneRepository laneRepository;

    @BeforeEach
    public void init() {
        setUp(
                1
        );
    }

    @Test
    @DisplayName("레인 전체 조회")
    void selectAll() {
        var lanes = laneRepository.findAll();
        assertNotEquals(lanes.size(), 0);

        assertEquals(lanes.get(0).getLaneNumber(), 1);

    }

    @Test
    @DisplayName("레인 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Lane> optionalLane = laneRepository.findById(1L);

        if (optionalLane.isPresent()) {
            Lane result = optionalLane.get();

            assertEquals(result.getLaneNumber(), 1);

            Integer newLaneNumber = 2;
            result.setLaneNumber(newLaneNumber);
            Lane updateLane = entityManager.persist(result);

            assertEquals(updateLane.getLaneNumber(), newLaneNumber);
        } else {
            assertNotNull(optionalLane);
        }
    }

    @Test
    @DisplayName("레인 추가 및 삭제")
    void insertAndDelete() {
        Lane setUp = setUp(
                2
        );
        Optional<Lane> optionalLane = laneRepository.findById(setUp.getId());

        if (optionalLane.isPresent()) {
            Lane lane = optionalLane.get();
            assertEquals(lane.getLaneNumber(), 2);

            entityManager.remove(lane);
            Optional<Lane> removeLane = laneRepository.findById(setUp.getId());

            removeLane.ifPresent(Assertions::assertNull);
        }
    }

    public Lane setUp(
            Integer laneNumber
    ) {
        Lane lane = new Lane(
                null,
                laneNumber,
                null,
                null,
                null,
                null
        );
        return entityManager.persist(lane);
    }
}
