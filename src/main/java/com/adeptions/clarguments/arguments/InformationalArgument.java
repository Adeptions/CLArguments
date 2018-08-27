package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

/**
 * Represents an informational argument
 * An informational argument is not expected to have a value (similar to flag argument)
 */
public class InformationalArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
    /**
     * Constructs an InformationalArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public InformationalArgument(Arguments parentArguments, ArgumentDefinition<Boolean> definition) {
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