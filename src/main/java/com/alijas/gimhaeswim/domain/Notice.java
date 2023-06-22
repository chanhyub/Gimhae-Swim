package com.alijas.gimhaeswim.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @Column(name = "notice_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeSeq;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "registration_date", nullable = false)
    private String registrationDate;

    @Column(name = "notice_writer", nullable = false)
    private String noticeWriter = "관리자";

    @Column(name = "notice_detail", nullable = false)
    private String noticeDetail;
}
