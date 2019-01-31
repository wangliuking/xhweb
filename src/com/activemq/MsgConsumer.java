package com.activemq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
 



import org.apache.activemq.ActiveMQConnectionFactory;


public class MsgConsumer {
	 //1、创建工厂连接对象，需要制定ip和端口号
	 
	public static void main(String[] args) throws JMSException {
 
		//1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:6666");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("RTU");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("send one message");
        //8、发送消息
        producer.send(textMessage);
        System.out.println("已发送-》"+textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
 
	}
 
	/**
	 * 消费者需要实现MessageListener接口 接口有一个onMessage(Message message)需要在此方法中做消息的处理
	 */
	public void onMessage(Message msg) {
		TextMessage txtMessage = (TextMessage) msg;
		try {
			System.out.println("get message:" + txtMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
 
	}


}
