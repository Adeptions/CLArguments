package com.adeptions.arguments;

public class FlagArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements IArgumentDefinition<Boolean> {
	public FlagArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.FLAG, name, description);
	}

	public FlagArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.FLAG, names, description);
	}

	public FlagArgument createArgumentInstance() {
		return new FlagArgument(this);
	}
}
