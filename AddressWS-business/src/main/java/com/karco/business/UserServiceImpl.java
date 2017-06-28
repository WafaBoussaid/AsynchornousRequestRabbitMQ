package com.karco.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karco.dao.UserRepository;
import com.karco.entities.User;
import com.karco.interfaces.UserBusiness;
@Service
public class UserServiceImpl implements UserBusiness{

	@Autowired
	private UserRepository userRepository; 
	@Override
	public User authenticate(String email, String password) {
		User user = new User();
		user.setId(0);
		User result = userRepository.autheticateUser(email, password);
		if(result != null && result.getId()>0)
			user = result;
		return user;
	}
	@Override
	public User add(User user){
		User result = userRepository.autheticateUser(user.getEmail(), user.getPassword());
		if(result.getId()==0){
			result = userRepository.save(user);
		}
		return result;
	}

}
