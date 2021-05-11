package com.zxh.springbootactivemqproviderdemo.controller;

import com.zxh.springbootactivemqproviderdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    @Autowired
    private JmsTemplate jmsTemplate;

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

    /**
     * 文件类型
     */
    @GetMapping("text")
    public void textMessage(){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("我是文本消息");
                textMessage.setStringProperty("订单","order");
                return textMessage;
            }
        });
    }

    /**
     * map类型
     */
    @GetMapping("map")
    public void mapMessage(){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("name","张三");
                mapMessage.setInt("age",20);
                return mapMessage;
            }
        });
    }

    /**
     * object类型
     */
    @GetMapping("obj")
    public void ObjectMessage(){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session.createObjectMessage();
                User user=new User("admin",20,"1234");
                message.setObject(user);
                return message;
            }
        });
    }

    /**
     * byte类型
     */
    @GetMapping("byte")
    public void BytesMessage(){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                BytesMessage message = session.createBytesMessage();
                try {
                    File file = new File("C:\\Users\\zhongyushi\\Pictures\\1.jpg");
                    FileInputStream stream=new FileInputStream(file);
                    byte[] bytes = new byte[(int)file.length()];
                    stream.read(bytes);
                    message.writeBytes(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return message;
            }
        });
    }

    /**
     * stream类型
     */
    @GetMapping("stream")
    public void streamMessage(){
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                StreamMessage streamMessage = session.createStreamMessage();
                streamMessage.writeString("张三");
                streamMessage.writeInt(20);
                return streamMessage;
            }
        });
    }
}
