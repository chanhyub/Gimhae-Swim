package com.alijas.gimhaeswim.module.photo.repository;

import com.alijas.gimhaeswim.module.notice.entity.Notice;
import com.alijas.gimhaeswim.module.notice.enums.NoticeStatus;
import com.alijas.gimhaeswim.module.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findAllByTitleContaining(Pageable pageable, String title);

    Page<Photo> findAllByContentContaining(Pageable pageable, String content);

    Page<Photo> findAllByTitleContainingOrContentContaining(Pageable pageable, String title, String content);
}
