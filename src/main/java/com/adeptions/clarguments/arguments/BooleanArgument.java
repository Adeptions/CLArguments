package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

/**
 * Represents a Boolean valued argument
 */
public class BooleanArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
    /**
     * Constructs a BooleanArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public BooleanArgument(Arguments parentArguments, ArgumentDefinition<Boolean> definition) {
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