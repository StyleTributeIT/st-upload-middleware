package com.styletribute.upload.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadDto implements Serializable{

    private static final long serialVersionUID = 3192437656089189922L;
    
    private String sku;
    private String label;   
}