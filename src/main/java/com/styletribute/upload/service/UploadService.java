package com.styletribute.upload.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.styletribute.upload.domain.FlashAirDto;
import com.styletribute.upload.domain.StatusEnum;
import com.styletribute.upload.domain.Upload;
import com.styletribute.upload.domain.UploadDto;
import com.styletribute.upload.repository.UploadRepository;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UploadService {
    
    @Autowired
    private UploadRepository uploadRepository;
    
    public void setUploader(Upload upload) {
        Upload flashAir = uploadRepository.findOneByFlashAirId(upload.getFlashAirId());
        if (flashAir != null) {
            if (flashAir.getFlashAirPass().equals(upload.getFlashAirPass())) {
                if (flashAir.getStatus().equals(StatusEnum.UNLOCK)){
                    flashAir.setSku(upload.getSku());
                    flashAir.setLabel(upload.getLabel());
                    flashAir.setMagentoUser(upload.getMagentoUser());
                    uploadRepository.save(flashAir);
                } else {
                    throw new IllegalArgumentException("flash air locked");                    
                }
            } else {
                throw new IllegalArgumentException("invalid flashair pass");
            }
        } else {
            throw new IllegalArgumentException("flash air id not found");
        }
    }
    
    public UploadDto getUploader(String flashAirId, String flashAirPass) {
        Upload flashAir = uploadRepository.findOneByFlashAirId(flashAirId);
        if (flashAir != null){
            if (flashAir.getFlashAirPass().equals(flashAirPass) == false){
                throw new IllegalArgumentException("wrong flashAir pass");
            }
        } else {
            throw new IllegalArgumentException("wrong flashAir id");
        }
        return new UploadDto(flashAir.getSku(), flashAir.getLabel());
    }
    
    public List<FlashAirDto> getAllUploaders() {
        return uploadRepository.findAll().stream().map(s-> {
          return new FlashAirDto(s.getFlashAirId(), s.getFlashAirPass());
        }).collect(Collectors.toList());
    }
}
