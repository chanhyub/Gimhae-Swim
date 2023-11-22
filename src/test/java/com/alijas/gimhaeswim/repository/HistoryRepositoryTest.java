package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.repository.HistoryRepository;
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
@DisplayName("HistoryRepositoryTest 테스트")
public class HistoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HistoryRepository historyRepository;

    @BeforeEach
    public void init() {
        setUp(
                "2002",
                "03",
                "김해수영연맹 창립"
        );
    }

    @Test
    @DisplayName("연혁 전체 조회")
    void selectAll() {
        var historys = historyRepository.findAll();
        assertNotEquals(historys.size(), 0);

        assertEquals(historys.get(0).getHistoryYear(), "2002");
        assertEquals(historys.get(0).getHistoryMonth(), "03");
        assertEquals(historys.get(0).getContent(), "김해수영연맹 창립");
    }

    @Test
    @DisplayName("연혁 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<History> optionalHistory = historyRepository.findById(1L);

        if (optionalHistory.isPresent()) {
            History result = optionalHistory.get();

            assertEquals(result.getHistoryYear(), "2002");
            assertEquals(result.getHistoryMonth(), "03");
            assertEquals(result.getContent(), "김해수영연맹 창립");

            String newMonth = "04";
            result.setHistoryMonth(newMonth);
            History updateHistory = entityManager.persist(result);

            assertEquals(updateHistory.getHistoryMonth(), newMonth);
        } else {
            assertNotNull(optionalHistory);
        }
    }

    @Test
    @DisplayName("연혁 추가 및 삭제")
    void insertAndDelete() {
        History setUp = setUp(
                "2005",
                "11",
                "제1회 김해시장배 수영마스터즈 대회 개최"
        );
        Optional<History> optionalHistory = historyRepository.findById(setUp.getId());

        if (optionalHistory.isPresent()) {
            History history = optionalHistory.get();
            assertEquals(history.getHistoryYear(), "2005");
            assertEquals(history.getHistoryMonth(), "11");
            assertEquals(history.getContent(), "제1회 김해시장배 수영마스터즈 대회 개최");

            entityManager.remove(history);
            Optional<History> removeHistory = historyRepository.findById(setUp.getId());

            removeHistory.ifPresent(Assertions::assertNull);
        }
    }

    public History setUp(
            String historyYear,
            String historyMonth,
            String content
    ) {
        History history = new History(
                null,
                historyYear,
                historyMonth,
                content
        );
        return entityManager.persist(history);
    }
}
