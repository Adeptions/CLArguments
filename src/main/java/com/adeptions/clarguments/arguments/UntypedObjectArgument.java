package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.definitions.*;

/**
 * Represents an Object (un-typed) valued argument
 */
public class UntypedObjectArgument extends AbstractArgument<Object> implements Argument<Object> {
	/**
	 * Constructs a UntypedObjectArgument with the specified parent arguments and argument definition
	 * @param parentArguments the arguments to which the argument belongs
	 * @param definition the definition of the argument
	 */
	public UntypedObjectArgument(Arguments parentArguments, ArgumentDefinition<Object> definition) {
		super(parentArguments, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRawValue(int tokenPosition, String rawValue, ArgName specifiedArgName) throws BadArgException {
		values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgName), this, specifiedArgName));
		specified = true;
	}
}
