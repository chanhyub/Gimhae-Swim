package com.alijas.gimhaeswim.example;

import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CompetitionExample {

    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

    Competition competition = new Competition(
            1L,
            "2021년 제1회 김해시장배 수영대회",
            DateTimeConverter.StringToLocalDateTime("2021-10-28 09:00:00"),
            "김해시 체육관",
            DateTimeConverter.StringToLocalDateTime("2021-10-01 00:00:00"),
            DateTimeConverter.StringToLocalDateTime("2021-10-20 23:59:59"),
            "2021년 제1회 김해시장배 수영대회",
            10000,
            5000,
            "신한은행 110-123-456789 홍길동",
            CompetitionStatus.ACTIVE);
}
