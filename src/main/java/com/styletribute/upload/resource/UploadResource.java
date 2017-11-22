package com.styletribute.upload.resource;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> getUploadDetail(@RequestBody String flashAirId, @RequestBody String flashAirPass) {
        ResponseEntity<?> resp = null;
        try{
            UploadDto uploadDto = uploadService.getUploader(flashAirId, flashAirPass);
            resp = ResponseEntity.ok(uploadDto);
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
    
    @RequestMapping(value = "set/signal/{flashAirId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<?> signal(@PathVariable String flashAirId) {
        ResponseEntity<?> resp = null;
        try{
            uploadService.notifyCameraClient(flashAirId);
            resp = ResponseEntity.ok("");
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
    
    @RequestMapping(value = "set/{flashAirId}/{flashAirPass}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @Transactional
    public ResponseEntity<?> upload(@PathVariable String flashAirId, @PathVariable String flashAirPass, @RequestParam("file") MultipartFile file) {
        ResponseEntity<?> resp = null;
        try{
            uploadService.uploadAndNotifyClient(flashAirId, flashAirPass, file);
            resp = ResponseEntity.ok("success");
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
}
