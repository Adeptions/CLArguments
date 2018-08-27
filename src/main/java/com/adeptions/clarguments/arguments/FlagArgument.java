package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

/**
 * Represents a flag argument
 * A flag argument is not expected to have a value but acquires a value of Boolean.TRUE when specified
 */
public class FlagArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
    /**
     * Constructs a FlagArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public FlagArgument(Arguments parentArguments, ArgumentDefinition<Boolean> definition) {
        super(parentArguments, definition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpecified() {
        specified = true;
        values.add(Boolean.TRUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRawValue(int tokenPosition, String rawValue, ArgumentName specifiedArgumentName) {
        specified = true;
        values.add(Boolean.TRUE);
    }
}