package com.adeptions.arguments;

public class ArgParsingException extends Exception {
	private ArgParsingExceptionReason reason;
	private IArgument argument;
	private ArgName specifiedArgName;

	public ArgParsingException(ArgParsingExceptionReason reason) {
		super();
		this.reason = reason;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message) {
		super(message);
		this.reason = reason;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, IArgument argument) {
		super(message);
		this.reason = reason;
		this.argument = argument;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, IArgument argument, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, Throwable cause) {
		super(message, cause);
		this.reason = reason;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, Throwable cause, IArgument argument, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgParsingException(ArgParsingExceptionReason reason, String message, Throwable cause, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgParsingException(Throwable cause) {
		super(cause);
	}

	public ArgParsingExceptionReason getReason() {
		return reason;
	}

	public IArgument getArgument() {
		return argument;
	}

	public ArgName getSpecifiedArgName() {
		return specifiedArgName;
	}
}
