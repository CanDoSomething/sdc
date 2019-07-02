package com.zgczx.mq;

import com.rabbitmq.client.Channel;
import com.zgczx.config.MQConfig;
import com.zgczx.dataobject.SubCourse;
import com.zgczx.dataobject.TeaCourse;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.SubCourseRepository;
import com.zgczx.repository.TeaCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Dqd
 * @Date: 2019/5/4 19:16
 * @Description:
 */

@Configuration
@Slf4j
@Component
public class DelayQueue {

    @Autowired
    private SubCourseRepository subCourseRepository;
    @Autowired
    private TeaCourseRepository teaCourseRepository;
    private String info;
    /**
     * 配置链接信息
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory
                = new CachingConnectionFactory(MQConfig.IP,MQConfig.PORT);

        connectionFactory.setUsername(MQConfig.USERNAME);
        connectionFactory.setPassword(MQConfig.PASSWD);
        connectionFactory.setVirtualHost(MQConfig.VHOST);
        connectionFactory.setPublisherConfirms(true);

        return connectionFactory;
    }

    /**
     * 配置消息交换机
     * 针对消费者配置
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(MQConfig.EXCHANGE, true, false);
    }

    /**
     * 配置消息队列2
     * 针对消费者配置
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(MQConfig.DELAY_QUEUE2, true);
    }
    /**
     * 将消息队列2与交换机绑定
     * 针对消费者配置
     */
    @Bean
    //@Autowired
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(MQConfig.ROUTINGKEY2);
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列1的消息
     * 针对消费者配置
     */
    @Bean
    @Autowired
    public SimpleMessageListenerContainer messageContainer2(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(MQConfig.MAXCONCURRENTCONSUMERS);
        container.setConcurrentConsumers(MQConfig.CONCURRENTCONSUMERS);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] courseIdByBytes = message.getBody();
                Integer courseId = Integer.valueOf(new String(courseIdByBytes));
                System.out.println("判断课程编号为:" + courseId + "的课程进入死信队列");
                //虽然是list但是预约成功的学生始终是一个
                List<SubCourse> rsCourse = subCourseRepository.findByCourseIdAndSubStatus(courseId, SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
                //如果有且只有一名学生预约课程成功，修改课程状态为正在进行
                if(null != rsCourse && rsCourse.size() == 1){
                    TeaCourse rsTeaCourse = teaCourseRepository.findOne(courseId);
                    if(null == rsTeaCourse){
                        info = "【获取延时队列中课程】 通过延迟队列中的课程编号没有找到对应课程信息";
                        log.error(info);
                        throw new SdcException(ResultEnum.INFO_NOTFOUND_EXCEPTION,info);
                    }

                    rsTeaCourse.setCourseStatus(CourseEnum.COURSE_INTERACT.getCode());
                    TeaCourse save = teaCourseRepository.save(rsTeaCourse);
                    if(null == save){
                        info = "【修改从延时队列取出的课程】 修改课程状态失败";
                        log.error(info);
                        throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
                    }
                }
                //确认消息成功消费
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }
}