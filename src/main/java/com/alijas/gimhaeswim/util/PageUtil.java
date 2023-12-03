package com.alijas.gimhaeswim.util;


import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public class PageUtil {
    public static void set(
            Pageable pageable, Model model, int boardPageTotalPages
    ) {
        int currentPage = pageable.getPageNumber();

        int basePage = 10;
        int startPage = (currentPage / basePage) * basePage + 1;
        int endPage = startPage + basePage - 1;

        if (endPage > boardPageTotalPages) {
            endPage = boardPageTotalPages;
        }

        if (startPage > endPage) {
            startPage = endPage;
        }

        int currentDivision = currentPage / basePage; // 49 / 10 = 4
        int totalDivision = boardPageTotalPages / basePage; // 55 / 10 = 5

        if (currentDivision == 0) {
            model.addAttribute("prev" , false);
        } else {
            model.addAttribute("prev" , true);
        }

        if (currentDivision < totalDivision) {
            model.addAttribute("next" , true);
        } else {
            model.addAttribute("next" , false);
        }

        if (basePage > currentPage) {
            startPage = 1;
        }

//        List<String> sortList = sort.stream()
//                .map(order -> order.getProperty() + "," + order.getDirection())
//                .toList();
//
//        StringBuilder sortStringBuilder = new StringBuilder();
//        for (int i = 0; i < sortList.size(); i++) {
//            if (i + 1 == sortList.size()) {
//                sortStringBuilder
//                        .append(sortList.get(i));
//            } else {
//                sortStringBuilder.append(sortList.get(i))
//                        .append("&sort=");
//            }
//        }

        model.addAttribute("basePage", basePage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
//        model.addAttribute("sort", sortStringBuilder.toString());

    }

}