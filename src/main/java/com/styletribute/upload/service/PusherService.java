package com.styletribute.upload.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pusher.rest.Pusher;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PusherService {
    @Value("${styletribute.pusher.appid}")
    private String appId;
    
    @Value("${styletribute.pusher.appkey}")
    private String appKey;
    
    @Value("${styletribute.pusher.appsecret}")
    private String appSecret;
    
    @Value("${styletribute.pusher.appcluster}")
    private String appCluster;
    
    private Pusher pusher = null;
    
    private Boolean trigger(String channel, String event, Object message) {
        this.init();
        try {
            pusher.trigger(channel, event, message);    
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    private void init() {
        if (pusher == null) {
            pusher = new Pusher(appId, appKey, appSecret);   
            pusher.setCluster(appCluster);
            pusher.setEncrypted(true);
        }
    }
    
    public void onImageUpload(String sku, String label, Object res) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sku", sku);
        map.put("label", label);
        if (res != null) {
            map.put("result", res.toString());
        } else {
            map.put("result", "");
        }
        this.trigger("uploader-channel", "image-upload", map);
    }
}
