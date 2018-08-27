package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.DoubleArgument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MISSING_VALUE;

/**
 * Represents the definition for a Double valued argument
 */
public class DoubleArgumentDefinition extends AbstractArgumentDefinition<Double> implements ArgumentDefinition<Double> {
    /**
     * Constructs a DoubleArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public DoubleArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("number");
    }

    /**
     * Constructs a DoubleArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public DoubleArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("number");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double convertRawValue(int tokenPosition, String rawValue, Argument<Double> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
        }
        try {
            Double result = Double.parseDouble(rawValue);
            if (result.isNaN()) {
                throw new NumberFormatException("Raw value was 'NaN'");
            }
            return result;
        } catch (NullPointerException nullPointerException) {
            throw new BadArgumentException(MISSING_VALUE, tokenPosition,
                    "Value missing", nullPointerException, argument, specifiedArgumentName);
        } catch (NumberFormatException numberFormatException) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value '" + rawValue + "' is not a valid number", numberFormatException, argument, specifiedArgumentName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<Double> createArgumentInstance(Arguments parentArguments) {
        return new DoubleArgument(parentArguments, this);
    }
}