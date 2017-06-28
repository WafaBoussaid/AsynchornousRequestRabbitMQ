package com.karco.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.karco.entities.Address;
import com.karco.util.MQHelper;

@RestController
public class AddressBusinessRestService {

	@Autowired
	private MQHelper mqHelper;

	@SuppressWarnings("unchecked")
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public List<Address> getAddressByFilter(@RequestParam String voie)
			throws JsonParseException, JsonMappingException, IOException {
		List<Address> addresses = mqHelper.sendAndReceive("getAddressByFilter", voie, List.class);
		return addresses;

	}
}
