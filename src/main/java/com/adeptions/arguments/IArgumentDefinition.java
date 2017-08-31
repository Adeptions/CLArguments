package com.adeptions.arguments;

import java.util.Set;

public interface IArgumentDefinition<T> {
	String getName();
	String getDisplayName(ArgsParsingOptions argsParsingOptions);
	String getDescription();
	String getFormat();
	String getHelp(ArgsParsingOptions argsParsingOptions);
	ArgumentDefinitionType getType();
	boolean isFlag();
	boolean isValued();
	boolean isInformational();
	boolean isMandatory();
	void setMandatory(boolean mandatory);
	boolean hasDefaultValue();
	T getDefaultValue();
	void setDefaultValue(T value);
	IArgumentValueValidator getValidator();
	void setValidator(IArgumentValueValidator valueValidator);
	Set<String> getAlternativeNames();
	T validate(T value, IArgument<T> argument, ArgName specifiedArgName) throws ArgsParsingException;

	IArgumentDefinition<T> addFormat(String format);
	IArgumentDefinition<T> addValidator(IArgumentValueValidator valueValidator);
	IArgumentDefinition<T> makeMandatory();
	IArgumentDefinition<T> addDefaultValue(T value);

	IArgument<T> createArgumentInstance();
}
