package com.topics.exception;

public class RegisterException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public RegisterException(String msg) {
		super(msg);
	}
}
