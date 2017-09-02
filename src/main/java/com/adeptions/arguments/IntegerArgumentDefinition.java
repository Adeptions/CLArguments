package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.INVALID_VALUE;

public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements ArgumentDefinition<Integer> {
	public IntegerArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("integer");
	}

	public IntegerArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("integer");
	}

	@Override
	public Integer convertRawValue(String rawValue, Argument<Integer> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		try {
			return Integer.parseInt(rawValue, 10);
		} catch (NumberFormatException numberFormatException) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid integer (for argument '" + specifiedArgName.getDisplayName() + "')", numberFormatException, argument, specifiedArgName);
		}
	}

	public Argument<Integer> createArgumentInstance() {
		return new IntegerArgument(this);
	}
}
