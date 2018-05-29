package com.zjy.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public String TestMain(){
        System.out.println("Hello World");
        return "Spring Hello World";
    }


}
