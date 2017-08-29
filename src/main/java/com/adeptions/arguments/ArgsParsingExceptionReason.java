package com.adeptions.arguments;

public enum ArgsParsingExceptionReason {
	MISSING_MANDATORY,
	MISSING_MANDATORIES, // one or more detected at end of parsing
	INVALID_VALUE,
	MISSING_VALUE,
	INVALID_ARGUMENT_NAME,
	UNKNOWN_ARGUMENT,
	UNKNOWN_ARGUMENT_VALUE,
	UNEXPECTED_EXCEPTION,
	UNDEFINED,
}
