package priv.dengjl.activemq.springjms;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class SpringJmsReceiver {
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:service-jms-reveiver.xml");
        // 阻塞方式
        JmsTemplate jmsTemplate=(JmsTemplate) context.getBean("jmsTemplate");

        String msg=(String)jmsTemplate.receiveAndConvert();

        System.out.println(msg);
    }
}
