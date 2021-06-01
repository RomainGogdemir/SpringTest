package com.example.userregistrationanddisplay;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will be thrown when the registration of a user is not authorized
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnauthorizedRegistrationException extends RuntimeException {
	
	public UnauthorizedRegistrationException(String exceptionMsg) {
		super(exceptionMsg);
	}
}
