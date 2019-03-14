package priv.dengjl.activemq.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producter {

	// 临时主机
	private static final String DEFAULT_BROKER_HOST = "192.168.1.104";
	private static final String DEFAULT_BROKER_PORT = "61616";

	// ActiveMq 的默认用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// ActiveMq 的默认登录密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// ActiveMQ 的链接地址
	private static final String BROKEN_URL = "tcp://" + DEFAULT_BROKER_HOST + ":" + DEFAULT_BROKER_PORT;

	AtomicInteger count = new AtomicInteger(0);
	// 链接工厂
	ConnectionFactory connectionFactory;
	// 链接对象
	Connection connection;
	// 事务管理
	Session session;

	public void init() {
		try {
			// 创建一个链接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
			// 从工厂中创建一个链接
			connection = connectionFactory.createConnection();
			// 开启链接
			connection.start();
			// 创建一个事务（这里通过参数可以设置事务的级别）
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String disname) {
		MessageProducer messageProducer = null;
		try {
			// 创建一个消息队列
			Queue queue = session.createQueue(disname);
			// 消息生产者
			messageProducer = session.createProducer(queue);
			for (int i = 0; i < 3; i++) {
				TimeUnit.SECONDS.sleep(1);
				int num = count.getAndIncrement();
				// 创建一条消息
				String message = Thread.currentThread().getName() + "productor:test！,count:" + num;
				System.out.println(message);
				TextMessage msg = session.createTextMessage(message);
				// 发送消息
				messageProducer.send(msg);
				// 提交事务
				session.commit();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (messageProducer != null) {
				try {
					messageProducer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}