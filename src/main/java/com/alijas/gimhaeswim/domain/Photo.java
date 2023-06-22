package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @Column(name = "photo_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoSeq;

    @Column(name = "photo_title", nullable = false)
    private String photoTitle;

    @Column(name = "registration_date", nullable = false)
    private String registrationDate;

    @Column(name = "photo_writer", nullable = false)
    private String photoWriter = "관리자";

    @Column(name = "photo_detail", nullable = false)
    private String photoDetail;

    @JsonManagedReference
    @OneToMany(mappedBy = "photo", orphanRemoval = true)
    List<PhotoImage> photoImageList = new ArrayList<>();
}
