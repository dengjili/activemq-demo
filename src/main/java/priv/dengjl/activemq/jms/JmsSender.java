package priv.dengjl.activemq.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsSender {
	public static void main(String[] args) {
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.103:61616");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://192.168.91.8:61616,tcp://192.168.91.8:61676)?randomize=false");

		System.out.println(connectionFactory);
		Connection connection = null;
		try {
			// 创建连接
			connection = connectionFactory.createConnection();
			connection.start();
			
			// 创建会话
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			// 创建队列，第一次才创建，first-queue表示名称，destination表示目的地
			Destination destination = session.createQueue("first-queue");
			MessageProducer producer = session.createProducer(destination);
			
			TextMessage textMessage = session.createTextMessage();
			for (int i = 0; i < 1; i++) {
				textMessage.setText("这是我的消息: " + i);
				textMessage.setStringProperty("name", "张三");
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
