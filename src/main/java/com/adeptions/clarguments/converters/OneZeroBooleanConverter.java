package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value converter for converting raw "1"/"0" values to boolean
 */
public class OneZeroBooleanConverter implements ArgumentValueConverter<Boolean> {
    private boolean allowNulls;
    private Boolean assumeNullValue;

    /**
     * Constructs an OneZeroBooleanConverter with default values
     */
    public OneZeroBooleanConverter() {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an OneZeroBooleanConverter with specified allowNulls
     * @param allowNulls whether nulls should be allowed
     */
    public OneZeroBooleanConverter(boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an OneZeroBooleanConverter with specified allowNulls and assumeNullValue
     * @param allowNulls whether nulls should be allowed
     * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
     */
    public OneZeroBooleanConverter(boolean allowNulls, Boolean assumeNullValue) {
        this.allowNulls = allowNulls;
        this.assumeNullValue = assumeNullValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convert(int tokenPosition, String rawValue, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (allowNulls && rawValue == null) {
            return assumeNullValue;
        }
        if (!"1".equals(rawValue) && !"0".equals(rawValue)) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value \"" + rawValue + "\" is invalid - must be either \"1\" or \"0\"", argument, specifiedArgumentName);
        }
        return "1".equals(rawValue);
    }
}