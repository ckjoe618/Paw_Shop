package com.topics.exception;

public class LoginFailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LoginFailException(String msg) {
		super(msg);
	}
}
