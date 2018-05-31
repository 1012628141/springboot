package com.zjy.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.zjy.springboot.config.ProjectConfig;
import com.zjy.springboot.model.pojo.UserInfo;
import com.zjy.springboot.service.UserService;
import com.zjy.springboot.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "user")
public class UserController {

    /*日志的使用方式*/
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${com.zjy.url.password}")
    private String password;

    @RequestMapping(value = "register")
    public String register(UserInfo userInfo) {
        int flag = userService.addUser(userInfo);
        return JsonResult.toString(200, "添加成功");
    }

    @RequestMapping(value = "all")
    public String selectAll() {
        List<UserInfo> userInfoList = userService.selectAll();
        logger.info("查询到的结果是" + JSON.toJSONString(userInfoList));
        logger.info("注入的参数name是：" + ProjectConfig.name);
        logger.info("注入的参数name是：" + password);
        return JsonResult.toString(200, "查询成功", userInfoList);
    }


}
