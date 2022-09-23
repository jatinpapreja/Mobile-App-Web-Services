package com.app.ws.exception;

public class UserServiceException extends RuntimeException{
	
	private static final long serialVersionUID = -8646498039909166217L;

	public UserServiceException(String message) {
		super(message);
	}

}
