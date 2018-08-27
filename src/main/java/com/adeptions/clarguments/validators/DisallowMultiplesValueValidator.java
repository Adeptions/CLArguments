package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MULTIPLE_ARGUMENT_NOT_ALLOWED;

public class DisallowMultiplesValueValidator implements ArgumentValueValidator {
    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate(int tokenPosition, Object value, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (argument.wasSeen()) {
            throw new BadArgumentException(MULTIPLE_ARGUMENT_NOT_ALLOWED, tokenPosition,
                    "Argument '" + specifiedArgumentName.getDisplayName() + "' cannot be specified more than once", argument, specifiedArgumentName);
        }
        return value;
    }
}