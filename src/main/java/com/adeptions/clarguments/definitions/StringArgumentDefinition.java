package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.StringArgument;

/**
 * Represents the definition for a String valued argument
 */
public class StringArgumentDefinition extends AbstractArgumentDefinition<String> implements ArgumentDefinition<String> {
    /**
     * Instantiates a new instance of a StringArgumentDefinition
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public StringArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("string");
    }

    /**
     * Instantiates a new instance of a StringArgumentDefinition
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public StringArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("string");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertRawValue(int tokenPosition, String rawValue, Argument<String> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
        }
        // no conversion necessary...
        return rawValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<String> createArgumentInstance(Arguments parentArguments) {
        return new StringArgument(parentArguments, this);
    }
}