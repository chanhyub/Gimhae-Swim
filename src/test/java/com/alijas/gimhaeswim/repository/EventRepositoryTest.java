package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.competition.entity.Event;
import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswim.module.competition.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("EventRepositoryTest 테스트")
public class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("종목 전체 조회")
    void selectAll() {
        var events = eventRepository.findAll();
        assertNotEquals(events.size(), 0);

        assertEquals(events.get(0).getEventName(), "접영");
        assertEquals(events.get(0).getStatus(), EventStatus.ACTIVE);
    }

    @Test
    @DisplayName("종목 상세 조회 및 수정")
    void selectAndUpdate() {
        Optional<Event> optionalEvent = eventRepository.findById(1L);

        if (optionalEvent.isPresent()) {
            Event result = optionalEvent.get();

            assertEquals(result.getEventName(), "접영");
            assertEquals(result.getStatus(), EventStatus.ACTIVE);

            String newEventName = "수정종목";
            result.setEventName(newEventName);
            Event updateEvent = entityManager.persist(result);

            assertEquals(updateEvent.getEventName(), newEventName);
        } else {
            assertNotNull(optionalEvent);
        }
    }

    @Test
    @DisplayName("종목 추가 및 삭제")
    void insertAndDelete() {
        Event setUp = setUp(
                "신규종목"
        );
        Optional<Event> optionalEvent = eventRepository.findById(setUp.getId());

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            assertEquals(event.getEventName(), "신규종목");
            assertEquals(event.getStatus(), EventStatus.ACTIVE);

            entityManager.remove(event);
            Optional<Event> removeEvent = eventRepository.findById(setUp.getId());

            removeEvent.ifPresent(Assertions::assertNull);
        }
    }

    public Event setUp(
            String eventName
    ) {
        Event event = new Event(
                null,
                eventName,
                EventStatus.ACTIVE
        );
        return entityManager.persist(event);
    }
}
