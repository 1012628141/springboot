package com.zjy.springboot.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.zjy.springboot.model.pojo.UserInfo;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqCustemer {

    private static final Logger logger = LoggerFactory.getLogger(MqProvider.class);

    /*{"body":"",
    "messageProperties":{"consumerQueue":"test_queues",
    "consumerTag":"amq.ctag-KogJZSOoHO_1kHCZa29VFQ","contentLength":0,
    "contentType":"application/octet-stream",
    "deliveryTag":1,"headers":{},
    "messageCount":0,
    "priority":0,
    "receivedDeliveryMode":"PERSISTENT",
    "receivedExchange":"",
    "receivedRoutingKey":
    "test_queues",
    "redelivered":false}}
*/
    /*在对象未序列化时候如果用Object对象接，会产生上述数据，原因未知，如果将对象实现可序列化，可以用同类型引用接受，实测*/
    @RabbitHandler
    @RabbitListener(queues = "test_queues")
    public void reciveMsg(UserInfo userInfo){
        String msg = JSON.toJSONString(userInfo);
        logger.info("[Rabbit:recive] 监听到消息队列的消息是："+msg);
    }



}
