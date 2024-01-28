package com.alijas.gimhaeswim.module.photo.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.file.entity.FileInfo;
import com.alijas.gimhaeswim.module.photo.dto.PhotoDTO;
import com.alijas.gimhaeswim.module.photo.dto.PhotoListDTO;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.util.DateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;

@Entity
@Table(name = "PHOTOS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Photo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    private Long id;

    @Comment("포토갤러리 제목")
    private String title;

    @Comment("포토갤러리 내용")
    private String content;

    @Comment("포토갤러리 사진")
    @ManyToOne
    private FileInfo fileInfo;

    @Comment("포토갤러리 작성자")
    @ManyToOne
    private User user;

    public PhotoListDTO toPhotoListDTO() {
        return new PhotoListDTO(this.id, this.title, this.content, null, user.getFullName(), DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.getCreatedDate()));
    }

    public PhotoDTO toPhotoDTO() {
        return new PhotoDTO(this.id, this.title, this.content, this.user.getFullName(), DateTimeConverter.LocalDateTimeToStringYYYYMMDD(this.getCreatedDate()), new ArrayList<>());
    }
}
