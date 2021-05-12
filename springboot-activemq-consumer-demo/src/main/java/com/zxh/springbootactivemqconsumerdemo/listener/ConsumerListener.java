package com.zxh.springbootactivemqconsumerdemo.listener;

import com.zxh.springbootactivemqconsumerdemo.entity.User;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.FileOutputStream;

@Component
public class ConsumerListener {

    @JmsListener(destination = "${activemq.queue}")
    public void receiveQueue(Message message,Session session) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("自定义属性："+textMessage.getStringProperty("订单"));
                System.out.println("收到message是：" + textMessage.getText());
                //提交事务
                session.commit();
            } else if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                System.out.println("收到message是：" + mapMessage.getString("name") + "," + mapMessage.getInt("age"));
            } else if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                User user = (User) objectMessage.getObject();
                System.out.println("收到message是：" + user.toString());
            } else if (message instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) message;
                byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
                bytesMessage.readBytes(bytes);
                FileOutputStream fileOutputStream = new FileOutputStream("D://1.jpg");
                fileOutputStream.write(bytes);
                System.out.println("保存了");
            }else if (message instanceof StreamMessage) {
                StreamMessage streamMessage = (StreamMessage) message;
                System.out.println("收到message是：" + streamMessage.readString() + "," + streamMessage.readInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException jmsException) {
                jmsException.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "${activemq.topic}")
    public void receiveTopic(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("收到的 topic message 是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
