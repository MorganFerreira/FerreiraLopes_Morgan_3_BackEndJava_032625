package com.p3backEnd.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("storage")
public class StorageProperties {  
    private String location = "uploads";
}