package com.dw.architecture1.ordermgr.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;

public class QueueSender {
    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "tcp://192.168.0.30:61616");

    public static void sendMsg(int customerUuid) {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("order-queue");
            ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session
                    .createProducer(destination);
            TextMessage message = session.createTextMessage(""+customerUuid);
            producer.send(message);
            session.commit();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}