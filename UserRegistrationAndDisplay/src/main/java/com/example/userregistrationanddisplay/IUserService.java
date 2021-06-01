package com.example.userregistrationanddisplay;

import java.net.URI;
import java.util.List;

public interface IUserService {
	
	/***
	 * This method returns the list of users which have the inputed name.
	 * @param name
	 * @return the list of User which name is inputed
	 */
	public List<User> findByName(String name);
	
	/***
	 * Deletes the user which have the inputed name.
	 * @param id of the user to be deleted 
	 */
	public void deleteById(String id);
	
	/***
	 * Registers a user.
	 * The user must be 18 or older and live in France.
	 * @param the User object to register
	 * @return the registered User object
	 * @throws UnauthorizedRegistrationException if the user is under 18 or not living in France
	 */
	public URI registerUser(User user) throws UnauthorizedRegistrationException;
	
	/***
	 * Returns the list of all users registered
	 * @return list of all User registered
	 */
	public List<User> findAllUsers();
	
}
