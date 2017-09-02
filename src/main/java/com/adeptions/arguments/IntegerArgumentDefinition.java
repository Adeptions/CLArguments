package com.adeptions.arguments;

public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements ArgumentDefinition<Integer> {
	public IntegerArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("integer");
	}

	public IntegerArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("integer");
	}

	public Argument<Integer> createArgumentInstance() {
		return new IntegerArgument(this);
	}
}
