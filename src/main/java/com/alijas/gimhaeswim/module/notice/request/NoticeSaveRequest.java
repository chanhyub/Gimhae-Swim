package com.alijas.gimhaeswim.module.notice.request;

import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import jakarta.validation.constraints.NotBlank;

public record NoticeSaveRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String content
//        Long fileId
) {
    public Notice toEntity() {
        return new Notice(
                null,
                title,
                content,
                null,
                NoticeStatus.ACTIVE,
                null
        );
    }
}
