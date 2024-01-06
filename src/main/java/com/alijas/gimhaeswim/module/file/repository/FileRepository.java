package com.alijas.gimhaeswim.module.file.repository;

import com.alijas.gimhaeswim.module.file.entity.File;
import com.alijas.gimhaeswim.module.file.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFileInfo(FileInfo fileInfo);
}
