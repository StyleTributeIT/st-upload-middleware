package com.styletribute.upload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private RequestService requestService;
    
    @Autowired
    private PusherService pusherService;
    
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
        return new UploadDto(flashAir.getSku(), flashAir.getLabel(), flashAir.getCurPhotoId());
    }
    
    public List<FlashAirDto> getAllUploaders() {
        return uploadRepository.findAll().stream().map(s-> {
          return new FlashAirDto(s.getFlashAirId(), s.getFlashAirPass());
        }).collect(Collectors.toList());
    }
    
    public String constructTempFile(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile(); 
            FileOutputStream fos = new FileOutputStream(convFile); 
            fos.write(file.getBytes());
            fos.close();
            return file.getOriginalFilename();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        } 
    }
    
    public  Boolean deleteTempFile(String fileName) {
        try {
            File f = new File(fileName);
            f.delete();
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot delete temp file");
        }
    }
    
    public void uploadAndNotifyClient(String flashAirId, String flashAirPass, MultipartFile file) {
        UploadDto uploadDto = this.getUploader(flashAirId, flashAirPass);
        
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        
        String tempfileName = this.constructTempFile(file);
        FileSystemResource fileSystem = new FileSystemResource(tempfileName);
        
        HttpHeaders headers = requestService.authHeader();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        Object res = null;
        if (uploadDto.getCurPhotoId() == null) {
            map.add("file", fileSystem);
            map.add("label", uploadDto.getLabel());
            res = requestService.post("admin/product/sku/" + uploadDto.getSku() + "/photos", map, headers);
        } else {
            map.add("file", fileSystem);
            res = requestService.post("admin/product/sku/" + uploadDto.getSku() + "/photos/" + uploadDto.getCurPhotoId().toString(), map, headers);    
        }
        
        pusherService.onImageUpload(uploadDto.getSku(), uploadDto.getLabel(), res);
        this.deleteTempFile(tempfileName);
    }
}
