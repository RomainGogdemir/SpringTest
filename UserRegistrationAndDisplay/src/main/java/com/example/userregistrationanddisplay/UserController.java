package com.example.userregistrationanddisplay;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user registration, delete and retrieval.
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	public final static String UNAUTHORIZED_REGISTRATION = "User must live in France and be older than 18 in order to register.";
	public final static String NO_USER_FOUND = "No user to display.";

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "user/{name}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<User> findByName(@PathVariable("name") String name) {
		if (name == null) {
			logger.info("No name provided, retrieving all users instead.");
			return findAllUsers();
		}
		logger.info("Looking for the user " + name + ".");
		long startTime = System.currentTimeMillis();
		List<User> user = userService.findByName(name);
		logger.info(
				"Looked for user : " + name 
				+ ", spent " + (System.currentTimeMillis() - startTime) + " milliseconds.");
		return user;
	}

	@GetMapping(value = "user/welcomeUser",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> welcomeUser(@RequestParam(required = false, defaultValue = "unknown user") String name) {
		long startTime = System.currentTimeMillis();
		logger.info("Greeting " + name + ".");
		ResponseEntity<String> response = ResponseEntity
				.accepted()
				.body("Welcome to this example application " + name + ".");
		logger.info("Greeted " + name + ", spent " + (System.currentTimeMillis() - startTime) + " milliseconds.");
		return response;
	}

	@GetMapping(value = "users",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAllUsers() {
		logger.info("Looking for all users.");
		long startTime = System.currentTimeMillis();

		List<User> users = userService.findAllUsers();

		StringBuilder sb = new StringBuilder();
		sb.append("Looked for all users, spent " + (System.currentTimeMillis() - startTime) + " milliseconds.");
		sb.append("Found :" + "\n");
		for (User u : users) {
			sb.append(u.toString() + "\n");
		}
		logger.info(sb.toString());

		return users;
	}

	@DeleteMapping(value = "user/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public void deleteById(@PathVariable("id") String id) {
		logger.info("Deleting user by id " + id + ".");
		long startTime = System.currentTimeMillis();
		userService.deleteById(id);
		logger.info("Deleted user " + id + ", spent " + (System.currentTimeMillis() - startTime) + " milliseconds.");
	}

	@PostMapping(value = "user",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		logger.info("Registering new user : " + user.toString() + ".");
		long startTime = System.currentTimeMillis();
		try {
			ResponseEntity<String> response = ResponseEntity.created(userService.registerUser(user)).build();
			logger.info("Registered the new user : " + user.toString() + ", spent "
					+ (System.currentTimeMillis() - startTime) + " milliseconds.");
			return response;
		} catch (UnauthorizedRegistrationException e) {
			ResponseEntity<String> response = ResponseEntity
					.unprocessableEntity()
					.body(UNAUTHORIZED_REGISTRATION);
			logger.info("Could not register the new : " + user.toString() 
			+ " for user must live in France and be older than 18 in order to register, spent "
			+ (System.currentTimeMillis() - startTime) + " milliseconds.");
			return response;
		}
	}

}
