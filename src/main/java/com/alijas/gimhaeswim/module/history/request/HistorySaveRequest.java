package com.alijas.gimhaeswim.module.history.request;

import com.alijas.gimhaeswim.module.history.entity.History;
import jakarta.validation.constraints.NotBlank;

public record HistorySaveRequest(
        @NotBlank(message = "연혁 년도를 입력해주세요.")
        String historyYear,
        @NotBlank(message = "연혁 월을 입력해주세요.")
        String historyMonth,
        @NotBlank(message = "연혁 내용을 입력해주세요.")
        String content
) {

    public History toEntity() {
        return new History(
                null,
                historyYear,
                historyMonth,
                content
        );
    }
}
