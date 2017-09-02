package com.adeptions.arguments;

import java.util.*;

public interface ArgumentDefinition<T> {
	String getName();
	Set<String> getAlternativeNames();
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
	ArgumentValueConverter<T> getValueConverter();
	void setValueConverter(ArgumentValueConverter<T> valueConverter);
	ArgumentValueValidator<T> getValueValidator();
	void setValueValidator(ArgumentValueValidator<T> valueValidator);

	T convertRawValue(String rawValue, Argument<T> argument, ArgName specifiedArgName) throws ArgParsingException;
	T validateValue(T value, Argument<T> argument, ArgName specifiedArgName) throws ArgParsingException;

	ArgumentDefinition<T> addFormat(String format);
	ArgumentDefinition<T> addValueConverter(ArgumentValueConverter<T> valueConverter);
	ArgumentDefinition<T> addValueValidator(ArgumentValueValidator<T> valueValidator);
	ArgumentDefinition<T> makeMandatory();
	ArgumentDefinition<T> addDefaultValue(T value);

	Argument<T> createArgumentInstance();
}
