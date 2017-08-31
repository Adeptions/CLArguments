package com.adeptions.arguments;

public class StringArgumentDefinition extends AbstractArgumentDefinition<String> implements IArgumentDefinition<String> {
	public StringArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("string");
	}

	public StringArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("string");
	}

	public IArgument<String> createArgumentInstance() {
		return new StringArgument(this);
	}
}
