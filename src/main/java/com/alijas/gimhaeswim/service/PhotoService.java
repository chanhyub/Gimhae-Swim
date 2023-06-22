package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.Photo;
import com.alijas.gimhaeswim.domain.PhotoImage;
import com.alijas.gimhaeswim.dto.PhotoDto;
import com.alijas.gimhaeswim.repository.PhotoImageRepository;
import com.alijas.gimhaeswim.repository.PhotoRepository;
import com.alijas.gimhaeswim.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final PhotoImageRepository photoImageRepository;
    private final S3Uploader s3Uploader;

    public void createPhoto(PhotoDto photoDto) throws IOException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());

        Photo photo = new Photo();
        photo.setPhotoTitle(photoDto.getPhotoTitle());
        photo.setPhotoDetail(photoDto.getPhotoDetail());
        photo.setRegistrationDate(formatter.format(date));
        photoRepository.save(photo);

        List<MultipartFile> photoList = photoDto.getPhotoList();
        int num = 1;
        for(MultipartFile img : photoList){
            PhotoImage photoImage = new PhotoImage();
            String imgUrl = s3Uploader.upload(img, "gimhae", photoDto.getPhotoTitle() + num);
            photoImage.setPhotoUrl(imgUrl);
            photoImage.setPhoto(photo);
            photoImageRepository.save(photoImage);
            num++;
        }



    }
    public Photo getPhoto(Integer photoSeq){
        Photo photo = photoRepository.findById(photoSeq).get();
        return photo;
    }
    public List<Photo> getPhotoList(){
        List<Photo> photoList = photoRepository.findAll();
        return photoList;
    }
    public void updatePhoto(String photoSeq, PhotoDto photoDto){

    }
    public void deletePhoto(Integer photoSeq){
        photoRepository.deleteById(photoSeq);
    }
}
