package com.zjy.springboot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfig {


    public static String name;

    @Value("${com.zjy.url.name}")
    public  void setName(String name) {
        ProjectConfig.name = name;
    }
}
