package com.alijas.gimhaeswim.module;

import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final CompetitionService competitionService;

    public ViewController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping({"/", ""})
    public String index() {


        return "index";
    }
}
