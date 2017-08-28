package com.adeptions.arguments;

public class IntegerArgument extends AbstractArgument<Integer> implements IArgument<Integer> {
	public IntegerArgument(IArgumentDefinition<Integer> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
		try {
			values.add(definition.validate(Integer.parseInt(rawValue), this, specifiedArgName));
			specified = true;
		} catch (NumberFormatException numberFormatException) {
			throw new ArgsParsingException("Value '" + rawValue + "' is not a valid integer (for argument '" + specifiedArgName.displayName + "')", numberFormatException, this, specifiedArgName);
		}
	}
}
