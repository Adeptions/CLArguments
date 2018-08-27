package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.InformationalArgument;

/**
 * Represents the definition for an informational argument
 */
public class InformationalArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
    /**
     * Constructs an InformationalArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public InformationalArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.INFORMATIONAL, name, description);
    }

    /**
     * Constructs an InformationalArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public InformationalArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.INFORMATIONAL, names, description);
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
    public InformationalArgument createArgumentInstance(Arguments parentArguments) {
        return new InformationalArgument(parentArguments, this);
    }
}