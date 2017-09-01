package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class IntegerArgument extends AbstractArgument<Integer> implements IArgument<Integer> {
	public IntegerArgument(IArgumentDefinition<Integer> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		try {
			values.add(definition.validate(Integer.parseInt(rawValue), this, specifiedArgName));
			specified = true;
		} catch (NumberFormatException numberFormatException) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid integer (for argument '" + specifiedArgName.getDisplayName() + "')", numberFormatException, this, specifiedArgName);
		}
	}
}
