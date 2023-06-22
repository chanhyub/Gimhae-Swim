package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Query(value="select * from record where competition_event_seq = ?1 order by record_seq asc" ,nativeQuery=true)
    List<Record> getRecord(Integer competitionEventSeq);
}
