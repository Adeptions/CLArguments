package com.adeptions.arguments;

public class FlagArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
	public FlagArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.FLAG, name, description);
	}

	public FlagArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.FLAG, names, description);
	}

	@Override
	public Boolean convertRawValue(String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		return true;
	}

	public FlagArgument createArgumentInstance() {
		return new FlagArgument(this);
	}
}
