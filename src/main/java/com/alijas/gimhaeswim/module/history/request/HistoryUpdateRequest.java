package com.alijas.gimhaeswim.module.history.request;

public record HistoryUpdateRequest(
        Long historyId,
        String historyYear,
        String historyMonth,
        String content
) {
}
