package com.adeptions.arguments;

import static com.adeptions.arguments.ArgsParsingExceptionReason.*;

public class BooleanArgument extends AbstractArgument<Boolean> implements IArgument<Boolean> {
	public BooleanArgument(IArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgsParsingException {
		if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
			throw new ArgsParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid boolean (for argument '" + specifiedArgName.getDisplayName() + "')", this);
		}
		specified = true;
		values.add(definition.validate("true".equals(rawValue), this, specifiedArgName));
	}
}
