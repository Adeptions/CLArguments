package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.BooleanArgument;
import com.adeptions.clarguments.converters.TrueFalseBooleanConverter;

/**
 * Represents the definition for a Boolean valued argument
 */
public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
    /**
     * Constructs a BooleanArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public BooleanArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("true|false");
    }

    /**
     * Constructs a BooleanArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public BooleanArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("true|false");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convertRawValue(int tokenPosition, String rawValue, Argument<Boolean> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter == null) {
            valueConverter = new TrueFalseBooleanConverter();
        }
        return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<Boolean> createArgumentInstance(Arguments parentArguments) {
        return new BooleanArgument(parentArguments, this);
    }
}