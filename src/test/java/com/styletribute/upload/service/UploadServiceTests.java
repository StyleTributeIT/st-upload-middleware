package com.styletribute.upload.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.styletribute.upload.domain.Upload;
import com.styletribute.upload.repository.UploadRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UploadServiceTests {
    
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UploadRepository uploadRepository;
    
    @Test
    @Sql({"classpath:/database/test/UploadService_uploadAndNotifyClient.SQL"})
    @Rollback
    public void uploadAndNotifyClient() {
        Upload upload = uploadRepository.getOne(100000000L);
        MultipartFile file = new MockMultipartFile("file", "logo.jpg", "image/jpeg", "ssssssss".getBytes());
        uploadService.uploadAndNotifyClient(upload.getFlashAirId(), upload.getFlashAirPass(), file);
    }
}
