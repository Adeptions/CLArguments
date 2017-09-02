package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.INVALID_VALUE;

public class DoubleArgumentDefinition extends AbstractArgumentDefinition<Double> implements ArgumentDefinition<Double> {
	public DoubleArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("number");
	}

	public DoubleArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("number");
	}

	@Override
	public Double convertRawValue(String rawValue, Argument<Double> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		try {
			Double result = Double.parseDouble(rawValue);
			if (result.isNaN()) {
				throw new NumberFormatException("Raw value was 'NaN'");
			}
			return result;
		} catch (NumberFormatException numberFormatException) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid number (for argument '" + specifiedArgName.getDisplayName() + "')", numberFormatException, argument, specifiedArgName);
		}
	}

	public Argument<Double> createArgumentInstance() {
		return new DoubleArgument(this);
	}
}
