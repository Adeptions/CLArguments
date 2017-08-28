package com.adeptions.arguments;

public class FlagArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements IArgumentDefinition<Boolean> {
	protected boolean hasDefaultValue;
	protected Boolean defaultValue;

	public FlagArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.FLAG, name, description);
	}

	public FlagArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.FLAG, names, description);
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

	public FlagArgument createArgumentInstance() {
		return new FlagArgument(this);
	}
}
