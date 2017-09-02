package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class IntegerArgument extends AbstractArgument<Integer> implements Argument<Integer> {
	public IntegerArgument(ArgumentDefinition<Integer> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validateValue(definition.convertRawValue(rawValue, this, specifiedArgName), this, specifiedArgName));
		specified = true;
	}
}
