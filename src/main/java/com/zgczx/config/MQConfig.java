package com.zgczx.config;

/**
 * @Author: Dqd
 * @Date: 2019/5/4 21:14
 * @Description:
 */
public class MQConfig {
    //交换机名称
    public static final String EXCHANGE = "delay";
    //队列key1
    public static final String ROUTINGKEY1 = "delay";
    //队列key2
    public static final String ROUTINGKEY2 = "delay_key";
    //主机ip
    public static final String IP = "127.0.0.1";
    //端口号
    public static final Integer PORT = 5672;
    //用户名
    public static final String USERNAME = "dqd";
    //密码
    public static final String PASSWD = "2918862" ;
    //VHOST
    public static final String VHOST = "/vhost_dqd";
    //最大当前消费者
    public static final Integer MAXCONCURRENTCONSUMERS = 1;
    //当前消费者数量
    public static final Integer CONCURRENTCONSUMERS = 1;
}
