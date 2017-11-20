package com.styletribute.upload.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="upload")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Upload implements Serializable{

    private static final long serialVersionUID = 3192437656089189922L;

    @Id
    @Column(name="upload_id")
    @SequenceGenerator(name="pk_sequence",sequenceName="upload_upload_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    private Long uploadId;
    
    @Column(name="flash_air_id")
    private String flashAirId;
    
    @Column(name="flash_air_pass")
    private String flashAirPass;
    
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    
    @Column(name="magento_user")
    private String magentoUser;
    
    @Column(name="sku")
    private String sku;
    
    @Column(name="label")
    private String label;   
    
    @Column(name="cur_photo_id")
    private Long curPhotoId;   
}
