package com.alijas.gimhaeswim.module.competition.repository;

import com.alijas.gimhaeswim.module.competition.entity.Event;
import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

        List<Event> findAllByStatus(EventStatus status);
}
