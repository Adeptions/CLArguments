package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value validator for checking an double value is within a specified range
 */
public class RangedDoubleValueValidator implements ArgumentValueValidator<Double> {
    private Double minValue;
    private Double maxValue;

    /**
     * Constructs a RangedDoubleValueValidator with the specified minimum and maximum values
     * @param minValue the minimum value (null for no minimum enforced)
     * @param maxValue the maximum value (null for no maximum enforced)
     */
    public RangedDoubleValueValidator(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double validate(int tokenPosition, Double value, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (value != null) {
            if (minValue != null && minValue.compareTo(value) > 0) {
                throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                        "Supplied value " + value + " must be greater than or equal to " + minValue, argument, specifiedArgumentName);
            }
            if (maxValue != null && maxValue.compareTo(value) < 0) {
                throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                        "Supplied value " + value + " must be less than or equal to " + maxValue, argument, specifiedArgumentName);
            }
        }
        return null;
    }
}