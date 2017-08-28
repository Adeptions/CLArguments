package com.adeptions.arguments;

public class ArgumentDefinitionException extends RuntimeException {
	public ArgumentDefinitionException() {
		super();
	}

	public ArgumentDefinitionException(String message) {
		super(message);
	}

	public ArgumentDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentDefinitionException(Throwable cause) {
		super(cause);
	}
}
