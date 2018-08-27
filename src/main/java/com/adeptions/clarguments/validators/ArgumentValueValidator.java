package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

/**
 * Interface for validating values
 */
@FunctionalInterface
public interface ArgumentValueValidator<T> {
    /**
     * Validates the value
     * @param tokenPosition the position of the token (in original args[])
     * @param value the value to be validated
     * @param argument the argument for which the value was found
     * @param specifiedArgumentName the name by which the argument was specified
     * @return the validated value (normally the same as was passed in)
     * @throws BadArgumentException if the validation fails
     */
    T validate(int tokenPosition, T value, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException;
}