package priv.dengjl.activemq.springjms;

import java.io.IOException;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

public class SpringJmsReceiver2Listener {
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:service-jms-reveiver.xml");
        // 非阻塞，监听方式
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
