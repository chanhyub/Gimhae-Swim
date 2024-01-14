package com.alijas.gimhaeswim.module.referee.service;

import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    public List<Referee> getRefereeList() {
        return refereeRepository.findAll();
    }
}
