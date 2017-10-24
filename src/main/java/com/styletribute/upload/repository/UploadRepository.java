package com.styletribute.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.styletribute.upload.domain.Upload;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    public Upload findOneByFlashAirId(String flashAirId);
}
