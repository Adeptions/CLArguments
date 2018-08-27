package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.IntegerArgument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Represents the definition for an Integer valued argument
 */
public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements ArgumentDefinition<Integer> {
    /**
     * Constructs an IntegerArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public IntegerArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("integer");
    }

    /**
     * Constructs an IntegerArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public IntegerArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("integer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer convertRawValue(int tokenPosition, String rawValue, Argument<Integer> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
        }
        try {
            return Integer.parseInt(rawValue, 10);
        } catch (NumberFormatException numberFormatException) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value '" + rawValue + "' is not a valid integer", numberFormatException, argument, specifiedArgumentName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<Integer> createArgumentInstance(Arguments parentArguments) {
        return new IntegerArgument(parentArguments, this);
    }
}