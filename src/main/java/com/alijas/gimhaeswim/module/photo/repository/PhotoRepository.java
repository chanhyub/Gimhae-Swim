package com.alijas.gimhaeswim.module.photo.repository;

import com.alijas.gimhaeswim.module.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
