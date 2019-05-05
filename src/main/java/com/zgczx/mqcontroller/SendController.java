package com.zgczx.mqcontroller;

import com.zgczx.config.MQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: Dqd
 * @Date: 2019/5/4 14:39
 * @Description:
 */

@RestController
public class SendController implements RabbitTemplate.ConfirmCallback{
    private RabbitTemplate rabbitTemplate;
    /**
     * 配置发送消息的rabbitTemplate，因为是构造方法，所以不用注解Spring也会自动注入（应该是新版本的特性）
     * @param rabbitTemplate
     */
    public SendController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //设置消费回调
        this.rabbitTemplate.setConfirmCallback(this);
    }
    /**
     * 向消息队列1中发送消息
     * @return
     */
    @RequestMapping("send1")
    public String send1(String msg1){
       /* String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(DelayQueue.EXCHANGE, DelayQueue.ROUTINGKEY1, msg1,
                correlationId);*/
        //-----
        String msg = "hello word";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("10000");
        messageProperties.setCorrelationId(UUID.randomUUID().toString().getBytes());
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("delay", "delay",message);
        return null;
    }
    /**
     * 向消息队列2中发送消息
     * @param msg
     * @return
     */
    @RequestMapping("send2")
    public String send2(String msg){
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTINGKEY2, msg,
                correlationId);
        return null;
    }
    /**
     * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("ACK为true");
        } else {
            System.out.println("ACK为false:" + cause+"\n重新发送");

        }
    }
}