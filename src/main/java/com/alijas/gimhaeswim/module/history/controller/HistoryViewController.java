package com.alijas.gimhaeswim.module.history.controller;

import com.alijas.gimhaeswim.module.history.dto.HistoryDTO;
import com.alijas.gimhaeswim.module.history.entity.History;
import com.alijas.gimhaeswim.module.history.service.HistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/histories")
public class HistoryViewController {

    private final HistoryService historyService;

    public HistoryViewController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping({"", "/"})
    public String history(Model model) {
        List<HistoryDTO> historyDTOList = historyService.getHistoryList(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(History::toDTO)
                .toList();

        model.addAttribute("historyList", historyDTOList);
        return "history/history";
    }
}
