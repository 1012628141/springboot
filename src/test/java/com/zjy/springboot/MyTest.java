package com.zjy.springboot;

import com.alibaba.fastjson.JSON;
import com.zjy.springboot.model.pojo.UserInfo;
import com.zjy.springboot.utils.SerializeUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/*  1.序列化，jdk原生序列化  需要实现Serializeble   将对象流转换成二进制文件流进行传输
    2.序列化成Json字符串   不需要实现Serializeble   将对象转换成字符串，
         字符串在传输时候已经实现了该接口
    3.dubbo实际上是服务注册在注册中心，消费者通过依赖注入，注入动态代理对象，
        当调用方法时，执行代理invoke方法，通过网络传输，将参数传递，返回结果给回调函数，给消费者*/
public class MyTest {

    @Test
    public void testJson() {
        UserInfo userInfo = new UserInfo();
        userInfo.setBirth(new Date());
        userInfo.setName("小明");
        userInfo.setEmail("1012628141@qq.com");
        userInfo.setGender(1);
        String s = JSON.toJSONString(userInfo);
        /*测试结果：序列化对象必须要实现Serializeble接口，而阿里的FastJson不需要实现序列化接口*/
        byte[] serialize = SerializeUtil.serialize(userInfo);
        System.out.println(Arrays.toString(serialize));
        System.out.println(s);
        String user="{\"birth\":\"2018-06-05 11:55:47\",\"email\":\"1012628141@qq.com\",\"gender\":1,\"name\":\"小明\"}";
       /*这样的序列化是可以直接反序列化成date类型的*/
        UserInfo userInfo1 = JSON.parseObject(user, UserInfo.class);
        System.out.println(userInfo1.getBirth());
    }


}
