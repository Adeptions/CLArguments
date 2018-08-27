package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value converter for converting raw "on"/"off" values to boolean
 */
public class OnOffBooleanConverter implements ArgumentValueConverter<Boolean> {
    private boolean allowNulls;
    private Boolean assumeNullValue;

    /**
     * Constructs an OnOffBooleanConverter with default values
     */
    public OnOffBooleanConverter() {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an OnOffBooleanConverter with specified allowNulls
     * @param allowNulls whether nulls should be allowed
     */
    public OnOffBooleanConverter(boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

    /**
     * Constructs an OnOffBooleanConverter with specified allowNulls and assumeNullValue
     * @param allowNulls whether nulls should be allowed
     * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
     */
    public OnOffBooleanConverter(boolean allowNulls, Boolean assumeNullValue) {
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
        if (!"on".equals(rawValue) && !"off".equals(rawValue)) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Value \"" + rawValue + "\" is invalid - must be either \"on\" or \"off\"", argument, specifiedArgumentName);
        }
        return "on".equals(rawValue);
    }
}