package com.adeptions.arguments;

public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements IArgumentDefinition<Integer> {
	public IntegerArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("integer");
	}

	public IntegerArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("integer");
	}

	public IArgument<Integer> createArgumentInstance() {
		return new IntegerArgument(this);
	}
}
