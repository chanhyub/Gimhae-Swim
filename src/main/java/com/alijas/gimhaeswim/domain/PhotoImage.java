package com.alijas.gimhaeswim.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "photo_image")
public class PhotoImage {
    @Id
    @Column(name = "photo_image_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoImageSeq;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "photo_seq")
    private Photo photo;
}
