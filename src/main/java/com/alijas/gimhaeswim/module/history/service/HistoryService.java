package com.alijas.gimhaeswim.module.history.service;

import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.repository.HistoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public List<History> getHistoryList(Sort sort) {
        return historyRepository.findAll(sort.descending());
    }
}
