package com.zjy.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.zjy.springboot.config.ProjectConfig;
import com.zjy.springboot.inteface.DubboInteface;
import com.zjy.springboot.model.pojo.UserInfo;
import com.zjy.springboot.rabbitmq.MqProvider;
import com.zjy.springboot.service.UserService;
import com.zjy.springboot.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "user")
public class UserController {

    /*日志的使用方式*/
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Resource
    private DubboInteface dubboService;

    @Autowired
    private MqProvider mqProvider;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${com.zjy.url.password}")
    private String password;


    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(UserInfo userInfo, MultipartFile file) {
        try {
            file.transferTo(new File("D://index.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        } //测试文件转储
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
    
    @RequestMapping(value = "testDubbo")
    public String testDubbo() {
        String test = dubboService.test();
        logger.info("注入dubbo是：" + test);
        return JsonResult.toString(200, "查询成功", test);
    }


    @RequestMapping(value = "testRabbitMq")
    public String testRabbitMq() {
        UserInfo userInfo = new UserInfo();
        userInfo.setBirth(new Date());
        userInfo.setName("小明");
        userInfo.setEmail("1012628141@qq.com");
        userInfo.setGender(1);
        rabbitTemplate.convertAndSend("test_queues",userInfo);

        /*经测试，rabbitMq如果传递对象也是通过序列化成二进制流，如果对象没有实现接口，虽然会生成消息，body中却没有内容，建议传递序列化对象或者序列化JsonString*/
        return JsonResult.toString(200, "查询成功");
    }
}
