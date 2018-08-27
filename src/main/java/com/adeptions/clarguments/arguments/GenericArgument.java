package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

/**
 * Represents a generic typed valued argument
 */
public class GenericArgument<T> extends AbstractArgument<T> implements Argument<T> {
    /**
     * Constructs a GenericArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public GenericArgument(Arguments parentArguments, ArgumentDefinition<T> definition) {
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
