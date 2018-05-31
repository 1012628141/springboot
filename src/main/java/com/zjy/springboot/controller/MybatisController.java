package com.zjy.springboot.controller;

import com.zjy.springboot.utils.JedisUtil;
import com.zjy.springboot.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")//测试mybatis与springboot整合
public class MybatisController {

    protected static Logger logger = LoggerFactory.getLogger(MybatisController.class);

    @RequestMapping(value = "redis")
    public String testRedis(){
        JedisUtil.setex("test_Springboot","hello springboot",1000);
        String ex = JedisUtil.get("test_Springboot");
        logger.info("Redis存储的内容是：["+ex+"]");
        return JsonResult.toString(200,"Jedis 存储成功",ex);
    }

}
