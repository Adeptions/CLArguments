package com.adeptions.arguments;

public class FlagArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
	public FlagArgument(ArgumentDefinition<Boolean> definition) {
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
