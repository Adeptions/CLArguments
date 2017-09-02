package com.adeptions.arguments;

public class StringArgument extends AbstractArgument<String> implements Argument<String> {
	public StringArgument(ArgumentDefinition<String> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validateValue(definition.convertRawValue(rawValue, this, specifiedArgName), this, specifiedArgName));
		specified = true;
	}
}
