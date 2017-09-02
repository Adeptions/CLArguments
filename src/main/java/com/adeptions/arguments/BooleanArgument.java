package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class BooleanArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
	public BooleanArgument(ArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid boolean (for argument '" + specifiedArgName.getDisplayName() + "')", this);
		}
		specified = true;
		values.add(definition.validate("true".equals(rawValue), this, specifiedArgName));
	}
}
