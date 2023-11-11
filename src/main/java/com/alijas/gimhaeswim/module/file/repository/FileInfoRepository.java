package com.alijas.gimhaeswim.module.file.repository;

import com.alijas.gimhaeswim.module.file.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
