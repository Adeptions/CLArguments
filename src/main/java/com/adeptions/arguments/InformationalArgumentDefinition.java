package com.adeptions.arguments;

public class InformationalArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
	public InformationalArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.INFORMATIONAL, name, description);
	}

	public InformationalArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.INFORMATIONAL, names, description);
	}

	@Override
	public Boolean convertRawValue(String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		return true;
	}

	public InformationalArgument createArgumentInstance() {
		return new InformationalArgument(this);
	}
}
