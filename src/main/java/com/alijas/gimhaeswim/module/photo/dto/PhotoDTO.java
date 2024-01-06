package com.alijas.gimhaeswim.module.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {

        private Long id;
        private String title;
        private String content;
        private String writer;
        private String createdDate;
        private List<String> fileUrl;
}
