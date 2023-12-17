package com.alijas.gimhaeswim.module.notice.dto;

public record NoticeDTO(
        Long id,
        String title,
        String content,
        String writer,
        String createdDate
) {
}
