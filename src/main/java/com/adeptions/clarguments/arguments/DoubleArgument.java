package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

/**
 * Represents a Double valued argument
 */
public class DoubleArgument extends AbstractArgument<Double> implements Argument<Double> {
    /**
     * Constructs a DoubleArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public DoubleArgument(Arguments parentArguments, ArgumentDefinition<Double> definition) {
        super(parentArguments,definition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRawValue(int tokenPosition, String rawValue, ArgumentName specifiedArgumentName) throws BadArgumentException {
        values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgumentName),this, specifiedArgumentName));
        specified = true;
    }
}