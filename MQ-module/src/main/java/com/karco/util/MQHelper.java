package com.karco.util;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karco.mq.domain.MqMessage;

@Service
public class MQHelper {

	@Autowired
	RabbitTemplate rabbitTemplate;

	private ObjectMapper mapper = new ObjectMapper();
	//The method following Send a message 
	//to a default exchange with a default routing key and attempt to receive a response.
	public <T> T sendAndReceive(String command, Object request, Class<T> responseType) throws IOException {
		MqMessage message = new MqMessage();
		message.setCommand(command);
		// Check if message is a string
		if (request instanceof String) {
			message.setRequest((String) request);
		} else {
			message.setRequest(mapper.writeValueAsString(request));
		}
		//To communicate the response in the network, JVM impose the format to a byte buffer
		byte[] resp = (byte[]) rabbitTemplate.convertSendAndReceive(message);
		System.out.println("***********************: " + resp.length);
		MqMessage responseMsg = mapper.readValue(new String(resp), MqMessage.class);
		T responseObj = mapper.readValue(responseMsg.getResponse(), responseType);
		return responseObj;
	}

}
