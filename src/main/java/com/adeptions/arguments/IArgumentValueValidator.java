package com.adeptions.arguments;

@FunctionalInterface
public interface IArgumentValueValidator {
	Object validate(Object value) throws ArgsParsingException;
}
