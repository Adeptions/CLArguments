package com.adeptions.arguments;

@FunctionalInterface
public interface ArgumentValueConverter<T> {
	T convert(String rawValue, Argument argument, ArgName specifiedArgName) throws ArgParsingException;
}
