package com.karco;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karco.business.UserServiceImpl;
import com.karco.dao.UserRepository;
import com.karco.entities.User;
import com.karco.interfaces.UserBusiness;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserServiceTest {

	@Configuration
	static class AccountServiceTestContextConfiguration {

		@Bean
		public UserBusiness userBusiness() {
			return new UserServiceImpl();
		}

		@Bean
		public UserRepository userRepository() {
			return Mockito.mock(UserRepository.class);
		}
	}

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		User user = new User() {
			{
				setId(3);
				setName("user");
				setPassword("pass");
			}
		};
		Mockito.when(userRepository.autheticateUser("user", "pass")).thenReturn(user);
	}

	@Test()
	public void testLoginFailure() {
		User user = userBusiness.authenticate("john", "fail");
		assertEquals(0, user.getId());
	}

	@Test()
	public void testLoginSuccess() {
		User user = userBusiness.authenticate("user", "pass");
		assertEquals("user", user.getName());
		assertEquals(3, user.getId());
	}

}
