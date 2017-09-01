package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class DoubleArgument extends AbstractArgument<Double> implements IArgument<Double> {
	public DoubleArgument(IArgumentDefinition<Double> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		try {
			values.add(definition.validate(Double.parseDouble(rawValue),this, specifiedArgName));
			specified = true;
		} catch (NumberFormatException numberFormatException) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid number (for argument '" + specifiedArgName.getDisplayName() + "')", numberFormatException, this, specifiedArgName);
		}
	}
}
