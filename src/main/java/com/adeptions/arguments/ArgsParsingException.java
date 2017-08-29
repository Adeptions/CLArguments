package com.adeptions.arguments;

public class ArgsParsingException extends Exception {
	private ArgsParsingExceptionReason reason;
	private IArgument argument;
	private ArgName specifiedArgName;

	public ArgsParsingException(ArgsParsingExceptionReason reason) {
		super();
		this.reason = reason;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message) {
		super(message);
		this.reason = reason;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, IArgument argument) {
		super(message);
		this.reason = reason;
		this.argument = argument;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, IArgument argument, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, Throwable cause) {
		super(message, cause);
		this.reason = reason;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, Throwable cause, IArgument argument, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(ArgsParsingExceptionReason reason, String message, Throwable cause, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(Throwable cause) {
		super(cause);
	}

	public ArgsParsingExceptionReason getReason() {
		return reason;
	}

	public IArgument getArgument() {
		return argument;
	}

	public ArgName getSpecifiedArgName() {
		return specifiedArgName;
	}
}
