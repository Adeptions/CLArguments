package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;

/**
 * Represents the definition for an Object (un-typed) valued argument
 */
public class UntypedObjectArgumentDefinition extends AbstractArgumentDefinition<Object> implements ArgumentDefinition<Object> {
	/**
	 * Constructs a UntypedObjectArgumentDefinition with the specified name and description
	 * @param name the name of the argument
	 * @param description the description of the argument
	 */
	public UntypedObjectArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addValueFormat("true|false");
	}

	/**
	 * Constructs a UntypedObjectArgumentDefinition with the specified names and description
	 * @param names the names of the argument
	 * @param description the description of the argument
	 */
	public UntypedObjectArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addValueFormat("true|false");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertRawValue(int tokenPosition, String rawValue, Argument<Object> argument, ArgName specifiedArgName) throws BadArgException {
		if (valueConverter == null) {
			return rawValue;
		}
		return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Argument<Object> createArgumentInstance(Arguments parentArguments) {
		return new UntypedObjectArgument(parentArguments, this);
	}
}
