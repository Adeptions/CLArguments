package com.adeptions.arguments;

import java.util.List;

public interface IArgument<T> {
	IArgumentDefinition<T> getDefinition();

	boolean isSpecified();
	void setSpecified();
	T getValue();
	void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException;
	void addValue(T value);
	List<T> getAllValues();
}
