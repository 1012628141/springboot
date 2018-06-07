package com.zjy.springboot.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqProvider {

    private static final Logger logger = LoggerFactory.getLogger(MqProvider.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*测试用服务提供类，实际有效在controller中测试*/
    public void sendMsg(String msg){
        logger.info("[Rabbit：send] 发送的信息为"+msg);
        rabbitTemplate.convertAndSend("test_queues",msg);
    }


}
