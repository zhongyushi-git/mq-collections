package com.zxh.springbootactivemqproviderdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

@RestController
public class ProviderController {
 
    //注入存放消息的队列
    @Autowired
    private Queue queue;

    //注入存放消息的队列
    @Autowired
    private Topic topic;
 
    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 点对点
     * @param name
     */
    @GetMapping("queue-send")
    public void queueSend(String name) {
        //发送消息
        jmsMessagingTemplate.convertAndSend(queue, name);
    }

    /**
     * 发布订阅
     * @param name
     */
    @GetMapping("topic-send")
    public void topicSend(String name) {
        //发送消息
        jmsMessagingTemplate.convertAndSend(topic, name);
    }
}
