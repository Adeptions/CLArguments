package com.adeptions.arguments;

public class ArgsParsingException extends Exception {
	private IArgument argument;
	private ArgName specifiedArgName;

	public ArgsParsingException() {
		super();
	}

	public ArgsParsingException(String message) {
		super(message);
	}

	public ArgsParsingException(String message, IArgument argument) {
		super(message);
		this.argument = argument;
	}

	public ArgsParsingException(String message, IArgument argument, ArgName specifiedArgName) {
		super(message);
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(String message, ArgName specifiedArgName) {
		super(message);
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgsParsingException(String message, Throwable cause, IArgument argument, ArgName specifiedArgName) {
		super(message, cause);
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(String message, Throwable cause, ArgName specifiedArgName) {
		super(message, cause);
		this.specifiedArgName = specifiedArgName;
	}

	public ArgsParsingException(Throwable cause) {
		super(cause);
	}

	public IArgument getArgument() {
		return argument;
	}

	public ArgName getSpecifiedArgName() {
		return specifiedArgName;
	}
}
