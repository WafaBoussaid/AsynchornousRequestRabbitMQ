package com.karco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.karco.entities.Vehicule;
import com.karco.interfaces.VehiculeBusiness;

@RestController
public class VehiculeBusinessRestService {
	
	@Autowired
	private VehiculeBusiness vehiculeBusiness;
	
	@CrossOrigin(origins = "*")
    @RequestMapping(value="/vehicules", method=RequestMethod.POST)    
	public Vehicule saveVehicule(@RequestBody Vehicule v) {
		return vehiculeBusiness.saveVehicule(v);
	}
    

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/vehicules", method=RequestMethod.GET)
	public List<Vehicule> ListVehicule() {
		return vehiculeBusiness.ListVehicule();
	}
    

	@CrossOrigin(origins = "http://localhost:8888")
    @RequestMapping(value="/vehiculebytype", method=RequestMethod.GET)
	public Vehicule getVehiculeByType(@RequestParam String type) {
		return vehiculeBusiness.getVehiculeByType(type);
    	
	}

	@CrossOrigin(origins = "http://localhost:8888")
    @RequestMapping(value="/vehiculeMaxPassenger", method=RequestMethod.GET)
	public int getMaxPassengerByType(@RequestParam String type) {
		return vehiculeBusiness.getMaxPassengerByType(type);
    	
	}
    
}
