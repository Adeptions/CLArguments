package com.adeptions.arguments;

import java.util.*;

public interface ArgumentDefinition<T> {
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
	ArgumentValueValidator getValidator();
	void setValidator(ArgumentValueValidator valueValidator);
	Set<String> getAlternativeNames();
	T validateValue(T value, Argument<T> argument, ArgName specifiedArgName) throws ArgParsingException;

	ArgumentDefinition<T> addFormat(String format);
	ArgumentDefinition<T> addValidator(ArgumentValueValidator valueValidator);
	ArgumentDefinition<T> makeMandatory();
	ArgumentDefinition<T> addDefaultValue(T value);

	Argument<T> createArgumentInstance();
}
