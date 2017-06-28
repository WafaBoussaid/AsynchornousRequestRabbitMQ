package com.karco;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.ExpectedBodyTypeException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.BeanFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import scala.annotation.meta.companionClass;

public class SingleConnectionFactoryTests {

	private static final String ROUTE = "test.queue";

	private static final String HOST = "localhost";

	private static final int PORT = 3456;

	private ConnectionFactory connectionFactory;
	private RabbitTemplate template;

	@Before
	public void create() {

	
		
		
		// c'est pour mocker les api
		this.connectionFactory = mock(ConnectionFactory.class);

		org.springframework.amqp.rabbit.connection.Connection connection = mock(
				org.springframework.amqp.rabbit.connection.Connection.class);
		Channel mockChannel = mock(Channel.class);

		when(connectionFactory.createConnection()).thenReturn(connection);
		when(connection.isOpen()).thenReturn(true);
		when(connection.createChannel(false)).thenReturn(mockChannel);
		//

		this.template = new RabbitTemplate(connectionFactory);
		BeanFactory bf = mock(BeanFactory.class);
		when(connectionFactory.getUsername()).thenReturn("guest");
		when(bf.getBean("cf")).thenReturn(connectionFactory);
		this.template.setBeanFactory(bf);
	}
//test the channel connection 
	@Test
	public void testWithChannelListener() throws Exception {

		com.rabbitmq.client.ConnectionFactory mockConnectionFactory = mock(com.rabbitmq.client.ConnectionFactory.class);
		com.rabbitmq.client.Connection mockConnection = mock(com.rabbitmq.client.Connection.class);
		Channel mockChannel = mock(Channel.class);
		ExecutorService es = mock(ExecutorService.class);

		when(mockConnectionFactory.newConnection(any(ExecutorService.class), anyString())).thenReturn(mockConnection);
		when(mockConnection.isOpen()).thenReturn(true);
		when(mockConnection.createChannel()).thenReturn(mockChannel);

		final AtomicInteger called = new AtomicInteger(0);
		Connection con = mockConnectionFactory.newConnection(es, "con1");
		Channel channel = con.createChannel();
		assertEquals(1, called.incrementAndGet());
		channel.close();

		verify(mockConnection, never()).close();

		mockConnectionFactory.newConnection(es, "con2");
		con.createChannel();
		assertEquals(2, called.incrementAndGet());

		mockConnection.close(anyInt());
		verify(mockConnection, atLeastOnce()).close(anyInt());

		verify(mockConnectionFactory, atMost(2)).newConnection(any(ExecutorService.class), anyString());

	}
	
	
	@Test(expected=org.springframework.amqp.AmqpException.class)
	public void testReceiveExceptionExpected() throws Exception {
		this.template.setUserIdExpressionString("@cf.username");
		this.template.convertAndSend(ROUTE, "block");
		Message received = this.template.receive(ROUTE, 10000);
		assertNull(received);
		assertEquals("block", new String(received.getBody()));
		assertThat(received.getMessageProperties().getReceivedUserId(), equalTo("guest"));
		this.template.setReceiveTimeout(0);
		assertNull(this.template.receive(ROUTE));
	}
	
	@Test(expected=org.springframework.amqp.AmqpException.class)
	public void testSendandReceive() throws Exception {
		this.template.setUserIdExpressionString("@cf.username");		
		Message received = this.template.receive(ROUTE, 0);
		this.template.sendAndReceive("block",received);
		assertNull(received);
		assertEquals("block", new String(received.getBody()));
		assertThat(received.getMessageProperties().getReceivedUserId(), equalTo("guest"));
		this.template.setReceiveTimeout(0);
		assertNull(this.template.receive(ROUTE));
	}

}