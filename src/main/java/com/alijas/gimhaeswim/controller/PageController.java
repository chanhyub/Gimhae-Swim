package com.alijas.gimhaeswim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class PageController {

    @RequestMapping("/group")
    public String group(){
        return "sub5_groupt.html";
    }

    @RequestMapping("/confirm")
    public String confirm(){
        return "sub2_confirmation.html";
    }

    @RequestMapping("/register")
    public String register(){
        return "sub3_useradd.html";
    }

}
