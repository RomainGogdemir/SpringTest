package com.example.userregistrationanddisplay;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	void shouldRegisterUser() {
		URI uri = null;
		User user = new User();
		user.setCountry("france");
		user.setJob("Boulanger");
		user.setBirthdate(LocalDate.of(2000, 1, 1));
		user.setName("Martin");
		user.setID("1");
		
		try {
			uri = userService.registerUser(user);
			
		} catch (UnauthorizedRegistrationException e) {
			fail();
		}
		Assert.assertNotNull(uri);
		Assert.assertTrue(uri.getPath().endsWith(user.getId()));
	}
	
	@Test
	void shouldRejectUserRegistrationCountry() {
		URI uri = null;
		User user = new User();
		user.setCountry("allemagne");
		user.setJob("Boulanger");
		user.setBirthdate(LocalDate.of(2000, 1, 1));
		user.setName("Martin");
		user.setID("1");
		
		try {
			uri = userService.registerUser(user);
			
		} catch (UnauthorizedRegistrationException e) { 
			Assert.assertTrue(e.getMessage().equals(userService.UNAUTHORIZED_REGISTRATION_ERROR_MESSAGE));
		}
	}
	
	@Test
	void shouldRejectUserRegistrationBirthdate() {
		URI uri = null;
		User user = new User();
		user.setCountry("france");
		user.setJob("Boulanger");
		user.setBirthdate(LocalDate.of(2020, 1, 1));
		user.setName("Martin");
		user.setID("1");
		
		try {
			uri = userService.registerUser(user);
		} catch (UnauthorizedRegistrationException e) { 
			Assert.assertTrue(e.getMessage().equals(userService.UNAUTHORIZED_REGISTRATION_ERROR_MESSAGE));
		}
	}

	@Test
	void shouldFindByName() {
		User user = new User();
		user.setCountry("france");
		user.setJob("Ingénieur");
		user.setBirthdate(LocalDate.of(2000, 1, 1));
		user.setName("Romain");
		user.setID("1");
		userService.registerUser(user);
		
		List<User> users = userService.findByName("Romain");
		
		Assert.assertEquals(1, users.size());
		Assert.assertTrue("Romain".equals(users.get(0).getName()));
		Assert.assertTrue("Ingénieur".equals(users.get(0).getJob()));
		Assert.assertTrue("france".equals(users.get(0).getCountry()));
		Assert.assertTrue(LocalDate.of(2000,1,1).equals(users.get(0).getBirthdate()));
		Assert.assertTrue("1".equals(users.get(0).getId()));
	}
	
	@Test
	void shouldDeleteById() {
		User userOne = new User();
		userOne.setCountry("france");
		userOne.setJob("Ingénieur");
		userOne.setBirthdate(LocalDate.of(2000, 1, 1));
		userOne.setName("Romain");
		userOne.setID("1");
		userService.registerUser(userOne);
		
		userService.deleteById("1");
		
		Assert.assertEquals(0, userService.findByName("Romain").size());
	}
	
	@Test
	void shouldFindAllUsers() {
		User userOne = new User();
		userOne.setCountry("france");
		userOne.setJob("Ingénieur");
		userOne.setBirthdate(LocalDate.of(2000, 1, 1));
		userOne.setName("Romain");
		userOne.setID("1");
		userService.registerUser(userOne);
		
		User userTwo = new User();
		userTwo.setCountry("france");
		userTwo.setBirthdate(LocalDate.of(2001, 1, 1));
		userTwo.setName("Thierry");
		userTwo.setID("2");
		userService.registerUser(userTwo);
		
		List<User> users = userService.findAllUsers();
		
		for (User user : users) {
			Assert.assertTrue(user.equals(userOne) || user.equals(userTwo));
		}
	}
}
