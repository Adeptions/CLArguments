package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value converter for converting raw "true"/"false" values to boolean
 */
public class TrueFalseBooleanConverter implements ArgumentValueConverter<Boolean> {
    private boolean allowNulls;
    private Boolean assumeNullValue;

    /**
     * Constructs an TrueFalseBooleanConverter with default values
     */
    public TrueFalseBooleanConverter() {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an TrueFalseBooleanConverter with specified allowNulls
     * @param allowNulls whether nulls should be allowed
     */
    public TrueFalseBooleanConverter(boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an TrueFalseBooleanConverter with specified allowNulls and assumeNullValue
     * @param allowNulls whether nulls should be allowed
     * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
     */
    public TrueFalseBooleanConverter(boolean allowNulls, Boolean assumeNullValue) {
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
        if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value \"" + rawValue + "\" is invalid - must be either \"true\" or \"false\"", argument, specifiedArgumentName);
        }
        return "true".equals(rawValue);
    }
}
