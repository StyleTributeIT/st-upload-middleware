package com.styletribute.upload.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadAdminDto implements Serializable{

    private static final long serialVersionUID = 3192437656089189922L;
    
    private List<FlashAirDto> flashAirDtos;
    private String adminUser;    
}
