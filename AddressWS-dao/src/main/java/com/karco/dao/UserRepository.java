package com.karco.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.karco.entities.*;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User,Integer>{


	@Query("Select u from User u where u.Email = :email and u.Password = :password")
	public User autheticateUser(@Param("email")String email,
								@Param("password")String password);
}
