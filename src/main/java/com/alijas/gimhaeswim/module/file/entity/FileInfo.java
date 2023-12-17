package com.alijas.gimhaeswim.module.file.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.file.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Table(name = "FILE_INFO")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends BaseTime {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("파일 출처")
    @Enumerated(EnumType.STRING)
    private FileType fileType;
}
