package com.alijas.gimhaeswim.module.photo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PhotoSaveRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String content,
        @NotNull(message = "사진을 첨부해 주세요.")
        List<MultipartFile> photoList
) {
}
