package com.adeptions.arguments;

public class InformationalArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
	public InformationalArgument(ArgumentDefinition<Boolean> definition) {
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
