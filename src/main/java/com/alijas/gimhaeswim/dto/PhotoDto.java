package com.alijas.gimhaeswim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhotoDto {
    private String photoTitle;
    private String photoDetail;
    private List<MultipartFile> photoList;
}
