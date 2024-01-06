package com.alijas.gimhaeswim.module.competition.service;

import com.alijas.gimhaeswim.module.competition.entity.Meter;
import com.alijas.gimhaeswim.module.competition.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterService {

    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public List<Meter> findAll() {
        return meterRepository.findAll();
    }
}
