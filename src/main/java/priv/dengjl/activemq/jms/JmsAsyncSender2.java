package priv.dengjl.activemq.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsAsyncSender2 {
	public static void main(String[] args) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://176.16.0.135:61616");
		System.out.println(connectionFactory);
		Connection connection = null;
		try {
			// 非持久化消息 同步发送（默认异步）
			connectionFactory.setAlwaysSyncSend(true);
			
			// 创建连接
			connection = connectionFactory.createConnection();
			connection.start();
			
			// 创建会话
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			// 创建队列，第一次才创建，first-queue表示名称，destination表示目的地
			Destination destination = session.createQueue("first-queue2");
			MessageProducer producer = session.createProducer(destination);
			
			TextMessage textMessage = session.createTextMessage();
			for (int i = 0; i < 1; i++) {
				textMessage.setText("异步消息: " + i);
				// 非持久化消息
				textMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
				producer.send(textMessage);
			}
			
			session.commit();
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
