package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.competition.entity.Meter;
import com.alijas.gimhaeswim.module.competition.repository.MeterRepository;
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
@DisplayName("MeterRepositoryTest 테스트")
public class MeterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MeterRepository meterRepository;

    @BeforeEach
    public void init() {
        setUp(
                "100M"
        );
    }

    @Test
    @DisplayName("미터 전체 조회")
    void selectAll() {
        var meters = meterRepository.findAll();
        assertNotEquals(meters.size(), 0);

        assertEquals(meters.get(0).getMeter(), "100M");
    }

    @Test
    @DisplayName("미터 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Meter> optionalMeter = meterRepository.findById(1L);

        if (optionalMeter.isPresent()) {
            Meter result = optionalMeter.get();

            assertEquals(result.getMeter(), "100M");

            String newMeter = "2000M";
            result.setMeter(newMeter);
            Meter updateMeter = entityManager.persist(result);

            assertEquals(updateMeter.getMeter(), newMeter);
        } else {
            assertNotNull(optionalMeter);
        }
    }

    @Test
    @DisplayName("미터 추가 및 삭제")
    void insertAndDelete() {
        Meter setUp = setUp(
                "3000M"
        );
        Optional<Meter> optionalMeter = meterRepository.findById(setUp.getId());

        if (optionalMeter.isPresent()) {
            Meter meter = optionalMeter.get();
            assertEquals(meter.getMeter(), "3000M");

            entityManager.remove(meter);
            Optional<Meter> removeMeter = meterRepository.findById(setUp.getId());

            removeMeter.ifPresent(Assertions::assertNull);
        }
    }

    public Meter setUp(
            String meter
    ) {
        Meter meterObject = new Meter(
                null,
                meter
        );
        return entityManager.persist(meterObject);
    }
}
