package com.zjy.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*测试页面跳转*/
/*static 目录下的文件上是可以直接访问到的，在控制层，return 路径只要正确，给文件完整的路径名，就可以访问到文件，参考*/
@Controller
@RequestMapping("page")
public class PageController {

    @RequestMapping(value = "{page}")
    public String jumpPage(@PathVariable("page") String page){
        System.out.println(page);
        return page;
    }



}
