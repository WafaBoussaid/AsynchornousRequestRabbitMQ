package com.karco.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karco.entities.Demande;
import com.karco.util.MQHelper;

@RestController
public class DemandeBusinessRestService {

	@Autowired
	private MQHelper mqHelper;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/mocksuggestions", method = RequestMethod.POST)
	public Demande getSuggestions(@RequestBody Demande demande) throws IOException {
		Demande response = mqHelper.sendAndReceive("mockSuggestions", demande, Demande.class);
		return response;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/suggestions", method = RequestMethod.POST)
	public Demande validate(@RequestBody Demande demande) throws IOException {
		Demande response = mqHelper.sendAndReceive("validateSuggestion", demande, Demande.class);
		return response;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getTripInfo", method = RequestMethod.POST)
	public Demande getTripInfo(@RequestBody Demande demande) throws IOException {
		Demande response = mqHelper.sendAndReceive("getTripInfo", demande, Demande.class);
		return response;
	}

}
