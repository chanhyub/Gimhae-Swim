package com.alijas.gimhaeswim.module.notice.entity;

import com.alijas.gimhaeswim.module.common.jpa.BaseTime;
import com.alijas.gimhaeswim.module.file.entity.FileInfo;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import com.alijas.gimhaeswim.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "NOTICES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공지사항 제목")
    private String title;

    @Comment("공지사항 내용")
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Comment("공지사항 작성자")
    @ManyToOne
    private User user;

    @Comment("공지사항 상태")
    @Enumerated(EnumType.STRING)
    private NoticeStatus status;

    @Comment("공지사항 첨부파일")
    @ManyToOne
    private FileInfo fileInfo;
}
