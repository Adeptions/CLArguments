package com.adeptions.arguments;

public class InformationalArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements IArgumentDefinition<Boolean> {
	public InformationalArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.INFORMATIONAL, name, description);
	}

	public InformationalArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.INFORMATIONAL, names, description);
	}

	public InformationalArgument createArgumentInstance() {
		return new InformationalArgument(this);
	}
}
