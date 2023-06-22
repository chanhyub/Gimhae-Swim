package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.History;
import com.alijas.gimhaeswim.domain.Notice;
import com.alijas.gimhaeswim.domain.Photo;
import com.alijas.gimhaeswim.dto.HistoryDto;
import com.alijas.gimhaeswim.dto.PhotoDto;
import com.alijas.gimhaeswim.repository.HistoryRepository;
import com.alijas.gimhaeswim.repository.PhotoRepository;
import com.alijas.gimhaeswim.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public void createHistory(HistoryDto historyDto){
        History history = new History();
        history.setHistoryYear(Integer.valueOf(historyDto.getYear()));
        history.setHistoryMonth(Integer.valueOf(historyDto.getMonth()));
        history.setDetail(historyDto.getDetail());
        historyRepository.save(history);
    }

    public History getHistory(Integer historySeq){
        History history = historyRepository.findById(historySeq).get();
        return history;
    }
    public List<History> getHistoryList(){
        List<History> historyList = historyRepository.getHistoryList();
        return historyList;
    }
    public void updateHistory(Integer historySeq, HistoryDto historyDto){
        History history = historyRepository.findById(historySeq).get();
        history.setHistoryYear(Integer.valueOf(historyDto.getYear()));
        history.setHistoryMonth(Integer.valueOf(historyDto.getMonth()));
        history.setDetail(historyDto.getDetail());
        historyRepository.save(history);
    }
    public void deleteHistory(Integer historySeq){
        historyRepository.deleteById(historySeq);
    }
}
