package com.adeptions.arguments;

public class InformationalArgument extends AbstractArgument<Boolean> implements IArgument<Boolean> {
	public InformationalArgument(IArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setSpecified() {
		specified = true;
		values.add(Boolean.TRUE);
	}

	@Override
	public void setRawValue(String rawValue, ArgName specifiedArgName) {
		specified = true;
		values.add(Boolean.TRUE);
	}
}
