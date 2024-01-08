package com.alijas.gimhaeswim.module.notice.dto;

public record NoticeListDTO(
        Long id,
        String title,
        String content,
        String createdYear,
        String createdMonthAndDay,
        String createdDate
) {
}
