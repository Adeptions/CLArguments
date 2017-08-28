package com.adeptions.arguments;

public class StringArgumentDefinition extends AbstractArgumentDefinition<String> implements IArgumentDefinition<String> {
	protected boolean hasDefaultValue;
	protected String defaultValue;

	public StringArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addFormat("string");
	}

	public StringArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addFormat("string");
	}

	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public IArgumentDefinition<String> setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		hasDefaultValue = true;
		return this;
	}

	public IArgument<String> createArgumentInstance() {
		return new StringArgument(this);
	}
}
