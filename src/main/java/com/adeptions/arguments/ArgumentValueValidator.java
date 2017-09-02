package com.adeptions.arguments;

@FunctionalInterface
public interface ArgumentValueValidator {
	Object validate(Object value, Argument argument, ArgName specifiedArgName) throws ArgParsingException;
}
