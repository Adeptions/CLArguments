package com.adeptions.arguments;

public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements IArgumentDefinition<Boolean> {
	protected boolean hasDefaultValue;
	protected Boolean defaultValue;

	BooleanArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("true|false");
	}

	BooleanArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("true|false");
	}

	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public Boolean getDefaultValue() {
		return defaultValue;
	}

	public IArgumentDefinition<Boolean> setDefaultValue(Boolean defaultValue) {
		this.defaultValue = defaultValue;
		hasDefaultValue = true;
		return this;
	}

	public IArgument<Boolean> createArgumentInstance() {
		return new BooleanArgument(this);
	}
}
