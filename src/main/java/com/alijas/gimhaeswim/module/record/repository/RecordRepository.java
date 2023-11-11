package com.alijas.gimhaeswim.module.record.repository;

import com.alijas.gimhaeswim.module.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
