package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.organization.entity.Organization;
import com.alijas.gimhaeswim.module.organization.enums.OrganizationStatus;
import com.alijas.gimhaeswim.module.record.entity.Record;
import com.alijas.gimhaeswim.module.record.repository.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("RecordRepositoryTest 테스트")
public class RecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecordRepository recordRepository;

    @BeforeEach
    public void init() {
        setUp(
                "03:45:23"
        );
    }

    @Test
    @DisplayName("기록 전체 조회")
    void selectAll() {
        var records = recordRepository.findAll();
        assertNotEquals(records.size(), 0);

        assertEquals(records.get(0).getRecord(), "03:45:23");
    }

    @Test
    @DisplayName("기록 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Record> optionalRecord = recordRepository.findById(1L);

        if (optionalRecord.isPresent()) {
            Record result = optionalRecord.get();

            assertEquals(result.getRecord(), "03:45:23");

            String newRecord = "03:45:12";
            result.setRecord(newRecord);
            Record updateRecord = entityManager.persist(result);

            assertEquals(updateRecord.getRecord(), newRecord);
        } else {
            assertNotNull(optionalRecord);
        }
    }

    @Test
    @DisplayName("기록 추가 및 삭제")
    void insertAndDelete() {
        Record setUp = setUp(
                "05:45:23"
        );
        Optional<Record> optionalRecord = recordRepository.findById(setUp.getId());

        if (optionalRecord.isPresent()) {
            Record record = optionalRecord.get();
            assertEquals(record.getRecord(), "05:45:23");

            entityManager.remove(record);
            Optional<Record> removeRecord = recordRepository.findById(setUp.getId());

            removeRecord.ifPresent(Assertions::assertNull);
        }
    }

    public Record setUp(
            String record
    ) {
        Record recordObject = new Record(
                null,
                null,
                null,
                record
        );
        return entityManager.persist(recordObject);
    }
}
