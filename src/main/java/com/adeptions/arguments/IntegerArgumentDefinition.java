package com.adeptions.arguments;

public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements IArgumentDefinition<Integer> {
	protected boolean hasDefaultValue;
	protected Integer defaultValue;

	public IntegerArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("integer");
	}

	public IntegerArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("integer");
	}

	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public IArgumentDefinition<Integer> setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
		hasDefaultValue = true;
		return this;
	}

	public IArgument<Integer> createArgumentInstance() {
		return new IntegerArgument(this);
	}
}
