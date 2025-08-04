package com.example.Drive_BE.repo;

import com.example.Drive_BE.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {
    boolean existsByNameAndParentFolderId(String name, Long parentFolderId);
}
