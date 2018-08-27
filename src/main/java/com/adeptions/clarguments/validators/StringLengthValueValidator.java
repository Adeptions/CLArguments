package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value validator for checking a string value is within length constraints (minimum and/or maximum)
 */
public class StringLengthValueValidator implements ArgumentValueValidator<String> {
    private Integer minLength;
    private Integer maxLength;

    /**
     * Constructs a StringLengthValueValidator with the specified minimum and maximum length values
     * @param minLength the minimum length of the string (null for no minimum enforced)
     * @param maxLength the maximum length of the string (null for no maximum enforced)
     */
    public StringLengthValueValidator(Integer minLength, Integer maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validate(int tokenPosition, String value, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (value != null) {
            if (minLength != null && value.length() < minLength) {
                throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                        "Supplied value length must be greater than or equal to " + minLength, argument, specifiedArgumentName);
            }
            if (maxLength != null && value.length() > maxLength) {
                throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                        "Supplied value length must be less than or equal to " + maxLength, argument, specifiedArgumentName);
            }
        } else if (minLength != null) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Supplied value length must be greater than or equal to " + minLength, argument, specifiedArgumentName);
        }
        return value;
    }
}