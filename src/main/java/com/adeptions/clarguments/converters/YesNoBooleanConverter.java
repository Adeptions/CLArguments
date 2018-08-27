package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value converter for converting raw "yes"/"no" values to boolean
 */
public class YesNoBooleanConverter implements ArgumentValueConverter<Boolean> {
    private boolean allowNulls;
    private Boolean assumeNullValue;

    /**
     * Constructs an YesNoBooleanConverter with default values
     */
    public YesNoBooleanConverter() {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an YesNoBooleanConverter with specified allowNulls
     * @param allowNulls whether nulls should be allowed
     */
    public YesNoBooleanConverter(boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an YesNoBooleanConverter with specified allowNulls and assumeNullValue
     * @param allowNulls whether nulls should be allowed
     * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
     */
    public YesNoBooleanConverter(boolean allowNulls, Boolean assumeNullValue) {
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
        if (!"yes".equals(rawValue) && !"no".equals(rawValue)) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value \"" + rawValue + "\" is invalid - must be either \"yes\" or \"no\"", argument, specifiedArgumentName);
        }
        return "yes".equals(rawValue);
    }
}