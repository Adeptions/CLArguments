package com.adeptions.arguments;

@FunctionalInterface
public interface ArgumentValueValidator<T> {
	T validate(T value, Argument argument, ArgName specifiedArgName) throws ArgParsingException;
}
