package com.zjy.springboot.inteface;


/*此接口测试dubbo注册服务    在分布式工程中，应该先定义一部分接口，作为服务的提供与消费者的共同资源   服务提供将接口与实现类注册到zookeeper
* 上，在这里，需要注意注册者的ip是否可以访问，如果是注册者和消费者不属于同一个网络，个人测试是不能调用的。
* 1.必要搭建的环境zookeeper配置相关环境，以级注意jar包
* 2.服务者，消费者，需要集成dubbo和zookeeper的相关依赖，配置文件可以采用xml方式，并且千万注意配置的参数
* 3.可选搭建dubbo服务治理框架，只是一个war包，用于管理zookeeper的服务，以及监控，注意jdk版本
* 4.根据实际情况去考虑负载均衡
* */
public interface DubboInteface {

    String test();

}
