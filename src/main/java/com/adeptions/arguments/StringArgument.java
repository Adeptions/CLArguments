package com.adeptions.arguments;

public class StringArgument extends AbstractArgument<String> implements IArgument<String> {
	public StringArgument(IArgumentDefinition<String> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
		values.add(definition.validate(rawValue, this, specifiedArgName));
		specified = true;
	}
}
