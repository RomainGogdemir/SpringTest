package com.example.userregistrationanddisplay;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Service dedicated to user registration, delete and retrieval.
 *
 */
@Component
public class UserService implements IUserService{

	public final static String FRANCE = "france";
	public final static String UNAUTHORIZED_REGISTRATION_ERROR_MESSAGE =
			"User must have be 18 or older and live in France to get registered.";
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		userRepository.deleteById(id);	
	}

	@Override
	public URI registerUser(User user) throws UnauthorizedRegistrationException {
		if (!canUserGetRegistered(user)) {
				throw new UnauthorizedRegistrationException(UNAUTHORIZED_REGISTRATION_ERROR_MESSAGE);
			}
			
			User userAdded = userRepository.save(user);
			if (userAdded == null) {
				return ServletUriComponentsBuilder
						.fromCurrentRequest()
						.build()
						.toUri();
			}
			
			return ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(userAdded.getId())
					.toUri();
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * @return true if the user is adult and lives in France.
	 * As this method is the only one checking functional conditions on the input, it stays here.
	 * In real conditions, a Helper or Util class would have been created to keep business rules 
	 * checks in a separated class and have responsibilities separated.  
	 */
	public boolean canUserGetRegistered(User user) {
		return FRANCE.equals(user.getCountry().toLowerCase())
				&& LocalDate.now().minusYears(18).minusDays(1).isAfter(user.getBirthdate());
	}

}
