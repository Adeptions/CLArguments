package com.adeptions.arguments;

public class DoubleArgument extends AbstractArgument<Double> implements IArgument<Double> {
	public DoubleArgument(IArgumentDefinition<Double> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
		try {
			values.add(definition.validate(Double.parseDouble(rawValue),this, specifiedArgName));
			specified = true;
		} catch (NumberFormatException numberFormatException) {
			throw new ArgsParsingException("Value '" + rawValue + "' is not a valid number (for argument '" + specifiedArgName.displayName + "')", numberFormatException, this, specifiedArgName);
		}
	}
}
