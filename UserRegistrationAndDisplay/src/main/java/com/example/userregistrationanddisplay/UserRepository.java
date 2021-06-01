
package com.example.userregistrationanddisplay;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * DAO for User object
 *
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends MongoRepository<User, String> {

	/***
	 * This method returns the list of users which have the inputed name.
	 * @param name
	 * @return the list of User which name is inputed
	 */
	List<User> findByName(@Param("name") String name);
	
	/***
	 * Returns the list of all users registered
	 * @return list of all User registered
	 */
	public List<User> findAll();
	
	/***
	 * Registers a user.
	 * @param the User object to register
	 * @return the registered User object
	 */
	public User save(User user);
}
