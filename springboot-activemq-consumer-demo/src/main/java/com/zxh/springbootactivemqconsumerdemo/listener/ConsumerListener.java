package com.zxh.springbootactivemqconsumerdemo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class ConsumerListener {

    @JmsListener(destination = "${activemq.queue}")
    public void receiveQueue(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage= (TextMessage) message;
            try {
                System.out.println("收到的 queue message 是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    @JmsListener(destination = "${activemq.topic}")
    public void receiveTopic(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage= (TextMessage) message;
            try {
                System.out.println("收到的 topic message 是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
