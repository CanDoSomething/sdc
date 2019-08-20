package com.zgczx.config;

/**
 * @Author: Dqd
 * @Date: 2019/7/1 15:46
 * @Description:
 */

public class MQConfig {
    //交换机名称
    public static final String EXCHANGE = "delay";
    //队列key1
    public static final String ROUTINGKEY1 = "delay";
    //队列key2
    public static final String ROUTINGKEY2 = "delay_key";
    //配置消息队列
    public static final String DELAY_QUEUE2 = "delay_queue2";
    //主机ip
    public static final String IP = "58.119.112.13";
    //端口号
    public static final Integer PORT = 5672;
    //用户名
    public static final String USERNAME = "admin";
    //密码
    public static final String PASSWD = "zkrtFCZ812" ;
    //VHOST
    public static final String VHOST = "/vhost_sdc";
    //最大当前消费者
    public static final Integer MAXCONCURRENTCONSUMERS = 1;
    //当前消费者数量
    public static final Integer CONCURRENTCONSUMERS = 1;
}