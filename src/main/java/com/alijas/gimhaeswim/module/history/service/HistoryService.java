package com.alijas.gimhaeswim.module.history.service;

import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.repository.HistoryRepository;
import com.alijas.gimhaeswim.module.history.request.HistorySaveRequest;
import com.alijas.gimhaeswim.module.history.request.HistoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public List<History> getHistoryList(Sort sort) {
        return historyRepository.findAll(sort.descending());
    }

    public Page<History> getHistoryList(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }

    public Optional<History> getHistory(Long id) {
        return historyRepository.findById(id);
    }

    @Transactional
    public void save(HistorySaveRequest historySaveRequest) {
        historyRepository.save(historySaveRequest.toEntity());
    }

    @Transactional
    public void update(History history, HistoryUpdateRequest historyUpdateRequest) {
        history.setHistoryMonth(historyUpdateRequest.historyMonth());
        history.setHistoryYear(historyUpdateRequest.historyYear());
        history.setContent(historyUpdateRequest.content());
        historyRepository.save(history);
    }

    @Transactional
    public void delete(History history) {
        historyRepository.delete(history);
    }
}
