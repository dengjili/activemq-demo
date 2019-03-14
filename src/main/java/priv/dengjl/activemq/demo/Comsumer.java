package priv.dengjl.activemq.demo;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Comsumer {

	// 临时主机
	private static final String DEFAULT_BROKER_HOST = "192.168.1.104";
	private static final String DEFAULT_BROKER_PORT = "61616";

	// ActiveMq 的默认用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// ActiveMq 的默认登录密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// ActiveMQ 的链接地址
	private static final String BROKEN_URL = "tcp://" + DEFAULT_BROKER_HOST + ":" + DEFAULT_BROKER_PORT;

	ConnectionFactory connectionFactory;

	Connection connection;

	Session session;

	AtomicInteger count = new AtomicInteger();

	public void init() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void getMessage(String disname) {
		try {
			Queue queue = session.createQueue(disname);
			MessageConsumer consumer = session.createConsumer(queue);
			while (true) {
				Thread.sleep(1000);
				TextMessage msg = (TextMessage) consumer.receive();
				if (msg != null) {
					msg.acknowledge();
					System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，我正在消费Msg" + msg.getText()
							+ "--->" + count.getAndIncrement());
				} else {
					break;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}