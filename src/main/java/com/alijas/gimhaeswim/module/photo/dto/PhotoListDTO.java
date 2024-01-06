package com.alijas.gimhaeswim.module.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoListDTO {

    private Long id;
    private String title;
    private String content;
    private String fileUrl;
    private String createdDate;
}
