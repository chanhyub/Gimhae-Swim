package com.alijas.gimhaeswim.module.photo.controller;

import com.alijas.gimhaeswim.module.photo.request.PhotoSaveRequest;
import com.alijas.gimhaeswim.module.photo.service.PhotoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

//    @PostMapping("/save")
//    public void save(PhotoSaveRequest photoSaveRequest) {
//        photoService.save(photoSaveRequest);
//    }

}
