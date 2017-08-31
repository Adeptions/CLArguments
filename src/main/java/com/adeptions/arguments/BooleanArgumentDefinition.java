package com.adeptions.arguments;

public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements IArgumentDefinition<Boolean> {
	BooleanArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("true|false");
	}

	BooleanArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("true|false");
	}

	public IArgument<Boolean> createArgumentInstance() {
		return new BooleanArgument(this);
	}
}
