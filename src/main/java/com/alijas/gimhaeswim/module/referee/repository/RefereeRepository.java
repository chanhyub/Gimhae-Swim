package com.alijas.gimhaeswim.module.referee.repository;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefereeRepository extends JpaRepository<Referee, Long> {
    List<Referee> findAllByStatus(ApplyStatus applyStatus);

    Page<Referee> findAllByStatus(Pageable pageable, ApplyStatus applyStatus);

    Optional<Referee> findByIdAndStatus(Long refereeId, ApplyStatus applyStatus);
}
