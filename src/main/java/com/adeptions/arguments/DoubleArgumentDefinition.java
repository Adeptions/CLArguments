package com.adeptions.arguments;

public class DoubleArgumentDefinition extends AbstractArgumentDefinition<Double> implements IArgumentDefinition<Double> {
	public DoubleArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("number");
	}

	public DoubleArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("number");
	}

	public IArgument<Double> createArgumentInstance() {
		return new DoubleArgument(this);
	}
}
