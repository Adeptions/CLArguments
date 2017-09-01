package com.adeptions.arguments;

public class StringArgument extends AbstractArgument<String> implements IArgument<String> {
	public StringArgument(IArgumentDefinition<String> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validate(rawValue, this, specifiedArgName));
		specified = true;
	}
}
