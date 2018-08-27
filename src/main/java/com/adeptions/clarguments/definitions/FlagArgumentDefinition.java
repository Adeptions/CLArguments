package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.FlagArgument;

/**
 * Represents the definition for a flag argument
 */
public class FlagArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
    /**
     * Constructs a FlagArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public FlagArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.FLAG, name, description);
    }

    /**
     * Constructs a FlagArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public FlagArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.FLAG, names, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convertRawValue(int tokenPosition, String rawValue, Argument<Boolean> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FlagArgument createArgumentInstance(Arguments parentArguments) {
        return new FlagArgument(parentArguments, this);
    }
}