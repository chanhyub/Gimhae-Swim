package com.alijas.gimhaeswim.module.photo.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.photo.dto.PhotoDTO;
import com.alijas.gimhaeswim.module.photo.dto.PhotoListDTO;
import com.alijas.gimhaeswim.module.photo.entity.Photo;
import com.alijas.gimhaeswim.module.photo.service.PhotoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoViewController {

    private final PhotoService photoService;

    public PhotoViewController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping({"", "/"})
    public String photoList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable,
            Model model
    ) {

        Page<PhotoListDTO> photoPage =  photoService.findAll(pageable);

        model.addAttribute("photoPage", photoPage);

        return "photo/photo";
    }

    @GetMapping("/{id}")
    public String photoDetail(
            Model model,
            @PathVariable Long id
    ) {
        Optional<Photo> optionalPhoto = photoService.getPhoto(id);
        if (optionalPhoto.isEmpty()) {
            throw new CustomException("존재하지 않는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }

        PhotoDTO photoDTO = photoService.getPhotoDetail(optionalPhoto.get());

        model.addAttribute("photo", photoDTO);

        return "photo/photoDetail";
    }



}
