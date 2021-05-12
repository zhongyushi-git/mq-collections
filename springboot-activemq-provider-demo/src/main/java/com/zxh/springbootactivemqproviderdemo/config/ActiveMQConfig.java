package com.zxh.springbootactivemqproviderdemo.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {

    @Value("${activemq.queue}")
    private String activemqQueue;

    @Value("${activemq.topic}")
    private String activemqTopic;

    //定义存放消息的队列
    @Bean
    public Queue queue() {
        return new ActiveMQQueue(activemqQueue);
    }

    //定义存放消息的主题
    @Bean
    public Topic topic() {
        return new ActiveMQTopic(activemqTopic);
    }


    /**
     * jms事务管理器
     * @param connectionFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager createTransactionManager(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory){
        return new JmsTransactionManager(connectionFactory);
    }


}
