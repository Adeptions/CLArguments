package com.adeptions.arguments;

@FunctionalInterface
public interface IArgumentValueValidator {
	Object validate(Object value, IArgument argument, ArgName specifiedArgName) throws ArgsParsingException;
}
