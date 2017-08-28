package com.adeptions.arguments;

public class FlagArgument extends AbstractArgument<Boolean> implements IArgument<Boolean> {
	public FlagArgument(IArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setSpecified() {
		specified = true;
		values.add(Boolean.TRUE);
	}

	@Override
	public void setRawValue(ArgName specifiedArgName, String rawValue) {
		specified = true;
		values.add(Boolean.TRUE);
	}
}
