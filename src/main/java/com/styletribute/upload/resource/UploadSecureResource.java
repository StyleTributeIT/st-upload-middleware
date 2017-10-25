package com.styletribute.upload.resource;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.styletribute.authenticate.domain.StyleAdmin;
import com.styletribute.authenticate.service.AuthenticationService;
import com.styletribute.upload.domain.Upload;
import com.styletribute.upload.domain.UploadAdminDto;
import com.styletribute.upload.service.UploadService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/secureuploader")
@CrossOrigin
@Log4j
public class UploadSecureResource {
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "set", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> setUploadDetail(Authentication authentication, @RequestBody Upload req) {
        ResponseEntity<?> resp = null;
        
        try{
            StyleAdmin user = (StyleAdmin) authenticationService.getAuthenticationDetails(authentication).get("ROLE_ADMIN");
            if (user != null){
                if(user.getUsername() != null){
                    if ("".equals(user.getUsername()) == false) {
                        req.setMagentoUser(user.getUsername());
                        uploadService.setUploader(req);     
                    }  
                }
            }
            resp = ResponseEntity.ok("success");
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<?> getUploadDetail(Authentication authentication) {
        ResponseEntity<?> resp = null;

        try{
            StyleAdmin user = (StyleAdmin) authenticationService.getAuthenticationDetails(authentication).get("ROLE_ADMIN");
            UploadAdminDto uploadAdmin = new UploadAdminDto();
            
            if (user != null){
                if(user.getUsername() != null){
                    if ("".equals(user.getUsername()) == false){
                        uploadAdmin.setFlashAirDtos(uploadService.getAllUploaders());
                        uploadAdmin.setAdminUser(user.getUsername());      
                    }  
                }
            }
            resp = ResponseEntity.ok(uploadAdmin);
        }catch(Exception e){
            resp = ResponseEntity.badRequest().body(e.getMessage());    
        }
        return resp;
    }
}
