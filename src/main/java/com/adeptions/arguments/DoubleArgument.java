package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class DoubleArgument extends AbstractArgument<Double> implements Argument<Double> {
	public DoubleArgument(ArgumentDefinition<Double> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validateValue(definition.convertRawValue(rawValue, this, specifiedArgName),this, specifiedArgName));
		specified = true;
	}
}
