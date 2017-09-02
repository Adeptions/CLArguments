package com.adeptions.arguments;

public class StringArgumentDefinition extends AbstractArgumentDefinition<String> implements ArgumentDefinition<String> {
	public StringArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("string");
	}

	public StringArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("string");
	}

	public Argument<String> createArgumentInstance() {
		return new StringArgument(this);
	}
}
