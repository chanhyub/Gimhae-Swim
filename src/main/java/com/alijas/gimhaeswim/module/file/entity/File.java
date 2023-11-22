package com.alijas.gimhaeswim.module.file.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.file.enums.FileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILES")
public class File extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    private Long id;

    @Comment("파일 출처")
    @ManyToOne
    private FileInfo fileInfo;

    @Comment("파일 이름")
    private String fileName;

    @Comment("파일 경로")
    private String fileUrl;

    @Comment("파일 사이즈")
    private Long fileSize;

    @Comment("파일 확장자")
    private String extension;

    @Comment("사진 활성화 상태")
    @Enumerated(EnumType.STRING)
    private FileStatus status;
}