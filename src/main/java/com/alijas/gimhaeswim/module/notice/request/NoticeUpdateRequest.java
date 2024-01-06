package com.alijas.gimhaeswim.module.notice.request;

import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoticeUpdateRequest(
    @NotBlank(message = "제목을 입력해주세요.")
    String title,
    @NotBlank(message = "내용을 입력해주세요.")
    String content,
    @NotNull(message = "존재하지 않는 공지사항입니다.")
    String noticeId
) {

    public Notice toEntity() {
        return new Notice(
            Long.parseLong(noticeId),
            title,
            content,
            null,
            NoticeStatus.ACTIVE,
            null
        );
    }
}
