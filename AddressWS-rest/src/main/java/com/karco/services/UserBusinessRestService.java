package com.karco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karco.entities.User;
import com.karco.interfaces.UserBusiness;

@RestController
public class UserBusinessRestService {
	
	@Autowired
	private UserBusiness userBusiness;

	@CrossOrigin(origins = "*")
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)    
	public User authenticate(@RequestBody User user) {
		return userBusiness.authenticate(user.getEmail(), user.getPassword());
	}
	

	@CrossOrigin(origins = "*")
	@RequestMapping(value="/user/authenticate", method=RequestMethod.POST)    
	public User authenticate(@RequestParam String email, String password) {
		return userBusiness.authenticate(email, password);
	}
    

	@CrossOrigin(origins = "http://localhost:8888")
	@RequestMapping(value="/add", method=RequestMethod.POST)    
	public User add(@RequestBody User user) {
		return userBusiness.add(user);
	}
    
    
    
    
}
