package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.INVALID_VALUE;

public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
	BooleanArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("true|false");
	}

	BooleanArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("true|false");
	}

	@Override
	public Boolean convertRawValue(String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid boolean (for argument '" + specifiedArgName.getDisplayName() + "')", argument, specifiedArgName);
		}
		return "true".equals(rawValue);
	}

	public Argument<Boolean> createArgumentInstance() {
		return new BooleanArgument(this);
	}
}
