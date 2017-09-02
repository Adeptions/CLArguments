package com.adeptions.arguments;

public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
	BooleanArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("true|false");
	}

	BooleanArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("true|false");
	}

	public Argument<Boolean> createArgumentInstance() {
		return new BooleanArgument(this);
	}
}
