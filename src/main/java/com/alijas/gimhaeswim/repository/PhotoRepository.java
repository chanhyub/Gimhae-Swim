package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
