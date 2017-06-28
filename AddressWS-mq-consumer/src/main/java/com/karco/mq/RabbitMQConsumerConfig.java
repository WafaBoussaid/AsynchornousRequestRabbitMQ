package com.karco.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.util.ErrorHandler;

import com.karco.mq.service.MessageListener;

@Configuration
@EnableAutoConfiguration
// making sure that Rabbit Listeners and other Rabbit configurations are
// detected and processed
@EnableRabbit
@ComponentScan
public class RabbitMQConsumerConfig implements RabbitListenerConfigurer {

	public final static String queueName = "karco_queue";

	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("karco_topic");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Autowired
	public ConnectionFactory connectionFactory;

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(rabbitErrorHandler());
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		return factory;
	}

	@Bean
	public ErrorHandler rabbitErrorHandler() {
		return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}

	public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

		@Override
		public boolean isFatal(Throwable t) {
			return true;
		}

	}
}
