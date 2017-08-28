package com.adeptions.arguments;

public class BooleanArgument extends AbstractArgument<Boolean> implements IArgument<Boolean> {
	public BooleanArgument(IArgumentDefinition<Boolean> definition) {
		super(definition);
	}

	@Override
	public void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
		if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
			throw new ArgsParsingException("Value '" + rawValue + "' is not a valid boolean (for argument '" + specifiedArgName.displayName + "')", this);
		}
		specified = true;
		values.add(definition.validate("true".equals(rawValue), this, specifiedArgName));
	}
}
