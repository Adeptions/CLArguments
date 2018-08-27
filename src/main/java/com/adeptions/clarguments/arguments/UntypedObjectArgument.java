package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

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
    public void setRawValue(int tokenPosition, String rawValue, ArgumentName specifiedArgumentName) throws BadArgumentException {
        values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgumentName), this, specifiedArgumentName));
        specified = true;
    }
}
