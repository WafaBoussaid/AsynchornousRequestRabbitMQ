package com.karco.interfaces;

import com.karco.entities.User;;

public interface UserBusiness {
	
	public User authenticate(String email, String passwordv);
	public User add(User user);
}
