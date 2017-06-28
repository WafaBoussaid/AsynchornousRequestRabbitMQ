package com.karco.mq.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import org.hibernate.boot.jaxb.SourceType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karco.entities.Demande;
import com.karco.interfaces.AddressBusiness;
import com.karco.interfaces.DemandeBusiness;
import com.karco.mq.RabbitMQConsumerConfig;
import com.karco.mq.domain.MqMessage;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class MessageListener {

	@Autowired
	private DemandeBusiness demandeBusiness;

	@Autowired
	private AddressBusiness addressBusiness;
//convert Java object to / from JSON
	private ObjectMapper mapper = new ObjectMapper();
	//Specifiying the queue target that the message listner will be listning on 
	 //read JSON messages to Java Objects using Spring Boot and RabbitMQ  
	
	@RabbitListener(queues = RabbitMQConsumerConfig.queueName)

	public byte[] receiveMessage(final MqMessage mqMessage)
			throws JsonParseException, JsonMappingException, IOException {
		Object responseObj = null;
		Demande demande = null;
	// Trace of the server and its adsress for further need 
	//of implementing diffrent servers to consume the message
		InetAddress host = InetAddress.getLocalHost();
		System.out.println("business server : " + host.getHostName()+"-"+host.getHostAddress());
		
		switch (mqMessage.getCommand()) {
		case "getAddressByFilter":
			String voie = parseRequest(mqMessage.getRequest(), String.class);
			responseObj = addressBusiness.getAddressByFilter(voie);
			break;
		case "mocksuggestions":
			demande = parseRequest(mqMessage.getRequest(), Demande.class);
			responseObj = demandeBusiness.mockSuggestions(demande);
			break;
		case "validateSuggestion":
			demande = parseRequest(mqMessage.getRequest(), Demande.class);
			responseObj = demandeBusiness.validateSuggestion(demande);
			break;
		case "getTripInfo":
			demande = parseRequest(mqMessage.getRequest(), Demande.class);
			responseObj = demandeBusiness.getTripInfo(demande);
			break;
		default:
			break;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, responseObj);
		mqMessage.setResponse(new String(out.toByteArray()));
		return mapper.writeValueAsBytes(mqMessage);
	}

	@SuppressWarnings("unchecked")
	private <T> T parseRequest(String request, Class<T> requestType)
			throws JsonParseException, JsonMappingException, IOException {
		if(requestType.equals(String.class)){
			return (T) request;
		}
		return mapper.readValue(request, requestType);
	}

}
