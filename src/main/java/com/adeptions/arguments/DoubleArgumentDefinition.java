package com.adeptions.arguments;

public class DoubleArgumentDefinition extends AbstractArgumentDefinition<Double> implements IArgumentDefinition<Double> {
	protected boolean hasDefaultValue;
	protected Double defaultValue;

	public DoubleArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("number");
	}

	public DoubleArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("number");
	}

	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public IArgumentDefinition<Double> setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
		hasDefaultValue = true;
		return this;
	}

	public IArgument<Double> createArgumentInstance() {
		return new DoubleArgument(this);
	}
}
