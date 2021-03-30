package org.dongx.projects.user.web.listener;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.MessageProducer;
import javax.jms.Topic;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Deprecated
public class TestComponent {

	@Resource(name = "jms/activemq-topic")
	private Topic topic;

	@Resource(name = "jms/message-producer")
	private MessageProducer messageProducer;

	@PostConstruct
	public void init() {
		System.out.println(topic);
	}

	@PostConstruct
	public void sendMessage() throws Throwable {
		// Create a messages
		String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
		ActiveMQTextMessage message = new ActiveMQTextMessage();
		message.setText(text);

		// Tell the producer to send the message
		messageProducer.send(message);
		System.out.printf("[Thread : %s] [TestComponent] Sent message : %s\n", Thread.currentThread().getName(), message.getText());
	}
}
