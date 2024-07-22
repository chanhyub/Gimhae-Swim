package com.alijas.gimhaeswim.module.photo.service;

import com.alijas.gimhaeswim.module.file.entity.File;
import com.alijas.gimhaeswim.module.file.entity.FileInfo;
import com.alijas.gimhaeswim.module.file.enums.FileStatus;
import com.alijas.gimhaeswim.module.file.enums.FileType;
import com.alijas.gimhaeswim.module.file.repository.FileInfoRepository;
import com.alijas.gimhaeswim.module.file.repository.FileRepository;
import com.alijas.gimhaeswim.module.photo.dto.PhotoDTO;
import com.alijas.gimhaeswim.module.photo.dto.PhotoListDTO;
import com.alijas.gimhaeswim.module.photo.entity.Photo;
import com.alijas.gimhaeswim.module.photo.repository.PhotoRepository;
import com.alijas.gimhaeswim.module.photo.request.PhotoSaveRequest;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.util.ParseMultipart;
import com.alijas.gimhaeswim.util.S3Uploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PhotoService {

    private final String PATH = "gimhae/photo-gallery/";

    private final PhotoRepository photoRepository;

    private final FileInfoRepository fileInfoRepository;

    private final FileRepository fileRepository;

    private final S3Uploader s3Uploader;

    public PhotoService(PhotoRepository photoRepository, FileInfoRepository fileInfoRepository, FileRepository fileRepository, S3Uploader s3Uploader) {
        this.photoRepository = photoRepository;
        this.fileInfoRepository = fileInfoRepository;
        this.fileRepository = fileRepository;
        this.s3Uploader = s3Uploader;
    }

    @Transactional
    public void save(PhotoSaveRequest photoSaveRequest, User user) {

        FileInfo photoFileInfo = fileInfoRepository.save(new FileInfo(null, FileType.PHOTO_GALLERY));


        photoSaveRequest.photoList().forEach(photo -> {
            try {
                String imageUrl = "";
                long imageSize = 0;

                imageUrl = s3Uploader.upload(photo, PATH);
                imageSize = photo.getInputStream().available();
                String fileName = photo.getOriginalFilename();

                fileRepository.save(new File(null, photoFileInfo, fileName, imageUrl, imageSize, ParseMultipart.getFileExtension(photo), FileStatus.ACTIVE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        imageUrl = s3Uploader.upload(photoSaveRequest.photoList(), "gimhae/photo-gallery/");
        photoRepository.save(new Photo(null, photoSaveRequest.title(), photoSaveRequest.content(), photoFileInfo, user));
    }

    public Page<PhotoListDTO> findAll(Pageable pageable) {
        Page<Photo> page = photoRepository.findAll(pageable);

        return page.map(photo -> {
            PhotoListDTO photoListDTO = photo.toPhotoListDTO();
            List<File> fileList = fileRepository.findByFileInfo(photo.getFileInfo());
            if (fileList.isEmpty()) {
                photoListDTO.setFileUrl("");
            } else {
                photoListDTO.setFileUrl(fileList.get(0).getFileUrl());
            }
            return photoListDTO;
        });
    }

    public Optional<Photo> getPhoto(Long id) {
        return photoRepository.findById(id);
    }

    public PhotoDTO getPhotoDetail(Photo photo) {
        PhotoDTO photoDTO = photo.toPhotoDTO();
        List<File> fileList = fileRepository.findByFileInfo(photo.getFileInfo());
        if (fileList.isEmpty()) {
//            photoDTO.setFileUrl("");
        } else {
            fileList.forEach(file -> {
                photoDTO.getFileUrl().add(file.getFileUrl());
            });
        }
        return photoDTO;
    }

    public Page<PhotoListDTO> getPhotoPage(Pageable pageable) {
        Page<Photo> photoPage = photoRepository.findAll(pageable);
        List<PhotoListDTO> list = photoPage.stream()
                .map(Photo::toPhotoListDTO)
                .toList();
        return new PageImpl<>(list, pageable, photoPage.getTotalElements());
    }

    @Transactional
    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }

    public Page<PhotoListDTO> findAllBySearchToTitle(Pageable pageable, String keyword) {
        Page<Photo> page = photoRepository.findAllByTitleContaining(pageable, keyword);

        return page.map(photo -> {
            PhotoListDTO photoListDTO = photo.toPhotoListDTO();
            List<File> fileList = fileRepository.findByFileInfo(photo.getFileInfo());
            if (fileList.isEmpty()) {
                photoListDTO.setFileUrl("");
            } else {
                photoListDTO.setFileUrl(fileList.get(0).getFileUrl());
            }
            return photoListDTO;
        });
    }


    public Page<PhotoListDTO> findAllBySearchToContent(Pageable pageable, String keyword) {
        Page<Photo> page = photoRepository.findAllByContentContaining(pageable, keyword);

        return page.map(photo -> {
            PhotoListDTO photoListDTO = photo.toPhotoListDTO();
            List<File> fileList = fileRepository.findByFileInfo(photo.getFileInfo());
            if (fileList.isEmpty()) {
                photoListDTO.setFileUrl("");
            } else {
                photoListDTO.setFileUrl(fileList.get(0).getFileUrl());
            }
            return photoListDTO;
        });
    }

    public Page<PhotoListDTO> findAllBySearchToTitleAndContent(Pageable pageable, String keyword) {
        Page<Photo> page = photoRepository.findAllByTitleContainingOrContentContaining(pageable, keyword, keyword);

        return page.map(photo -> {
            PhotoListDTO photoListDTO = photo.toPhotoListDTO();
            List<File> fileList = fileRepository.findByFileInfo(photo.getFileInfo());
            if (fileList.isEmpty()) {
                photoListDTO.setFileUrl("");
            } else {
                photoListDTO.setFileUrl(fileList.get(0).getFileUrl());
            }
            return photoListDTO;
        });
    }
}
