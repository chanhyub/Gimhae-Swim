package com.alijas.gimhaeswim.module.photo.controller;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.notice.dto.NoticeListDTO;
import com.alijas.gimhaeswim.module.photo.dto.PhotoDTO;
import com.alijas.gimhaeswim.module.photo.dto.PhotoListDTO;
import com.alijas.gimhaeswim.module.photo.entity.Photo;
import com.alijas.gimhaeswim.module.photo.service.PhotoService;
import com.alijas.gimhaeswim.util.PageUtil;
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
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            Model model
    ) {
        Page<PhotoListDTO> photoPage;
        if (!searchType.isEmpty() && !keyword.isEmpty()) {
            if (searchType.equals("0")) {
                // 제목
                photoPage = photoService.findAllBySearchToTitle(pageable, keyword);
            } else if (searchType.equals("1")) {
                // 내용
                photoPage = photoService.findAllBySearchToContent(pageable, keyword);
            } else if (searchType.equals("2")) {
                // 제목 + 내용
                photoPage = photoService.findAllBySearchToTitleAndContent(pageable, keyword);
            } else {
                throw new CustomException("잘못된 검색 조건입니다.", HttpStatus.BAD_REQUEST);
            }
            PageUtil.set(pageable, model, photoPage.getTotalPages());
            model.addAttribute("photoPage", photoPage);
            model.addAttribute("searchType", searchType);
            model.addAttribute("keyword", keyword);

            return "photo/photo";
        }

        photoPage =  photoService.findAll(pageable);
        PageUtil.set(pageable, model, photoPage.getTotalPages());
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
