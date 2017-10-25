package com.styletribute.upload.resource;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.styletribute.upload.domain.UploadDto;
import com.styletribute.upload.service.UploadService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/uploader")
@CrossOrigin
@Log4j
public class UploadResource {
    
    @Autowired
    private UploadService uploadService;
    
    @RequestMapping(value = "get/{flashAirId}/{flashAirPass}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<?> getUploadDetail(@PathVariable String flashAirId, @PathVariable String flashAirPass) {
        ResponseEntity<?> resp = null;
        try{
            UploadDto uploadDto = uploadService.getUploader(flashAirId, flashAirPass);
            resp = ResponseEntity.ok(uploadDto);
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
}
