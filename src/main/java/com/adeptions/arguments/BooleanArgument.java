package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class BooleanArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
	public BooleanArgument(ArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validateValue(definition.convertRawValue(rawValue, this, specifiedArgName), this, specifiedArgName));
		specified = true;
	}
}
