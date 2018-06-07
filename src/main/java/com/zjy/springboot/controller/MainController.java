package com.zjy.springboot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//是@ResponseBody @Controller的结合 作用在于整个类返回Json格式的返回值，并且序列化
@RequestMapping("index")
public class MainController {

    @RequestMapping(value = "/")
    public String TestMain(){
        System.out.println("Hello World");
        return "Spring Hello World";
    }

}
