package com.styletribute.upload.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Getter
@Setter
@Log4j
public class RequestService {

    @Value("${styletribute.api.url}")
    private String apiUrl;
    
    @Value("${styletribute.token.admin:''}")
    private String styletributeAdminToken;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object simpleApiRequest(String uri, Class returnClass, Object data, HttpHeaders httpHeaders, HttpMethod httpMethod) {
        
        Object returnObj = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = httpHeaders;
            HttpEntity requestEntity = new HttpEntity(data, requestHeaders);
            
            ResponseEntity resp = restTemplate.exchange(this.getApiUrl().concat(uri), httpMethod, requestEntity,
                    returnClass);

            Object obj = resp.getBody();

            if (obj != null) {
                returnObj = obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObj;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> Object simpleApiRequest(String uri, ParameterizedTypeReference<T> returnClass, Object data, HttpHeaders httpHeaders, HttpMethod httpMethod) {
        
        Object returnObj = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = httpHeaders;
            HttpEntity requestEntity = new HttpEntity(data, requestHeaders);

            ResponseEntity resp = restTemplate.exchange(this.getApiUrl().concat(uri), httpMethod, requestEntity,
                    returnClass);

            Object obj = resp.getBody();

            if (obj != null) {
                returnObj = obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObj;
    }
    
    @SuppressWarnings({ "rawtypes" })
    public Object get(String uri, Class returnClass) {
        return simpleApiRequest(uri, returnClass, null, new HttpHeaders(), HttpMethod.GET);
    }
    
    @SuppressWarnings({ "rawtypes" })
    public Object get(String uri, Class returnClass, HttpHeaders header) {
        return simpleApiRequest(uri, returnClass, null, header, HttpMethod.GET);
    }
    
    public <T> Object get(String uri, ParameterizedTypeReference<T> returnClass) {
        return simpleApiRequest(uri, returnClass, null, new HttpHeaders(), HttpMethod.GET);
    }
    
    public <T> Object get(String uri, ParameterizedTypeReference<T> returnClass, HttpHeaders header) {
        return simpleApiRequest(uri, returnClass, null, header, HttpMethod.GET);
    }
    
    public Object post(String uri, Object data, HttpHeaders header) {
        return simpleApiRequest(uri, Map.class, data, header, HttpMethod.POST);
    }
    
    public Object delete(String uri, Object data, HttpHeaders header) {
        return simpleApiRequest(uri, Map.class, data, header, HttpMethod.DELETE);
    }
    
    public HttpHeaders authHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Styletribute-Admin-Token", styletributeAdminToken);
        return httpHeaders;
    }
}
