package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

/**
 * Interface for converting incoming raw string arg values to appropriate type
 */
@FunctionalInterface
public interface ArgumentValueConverter<T> {
    /**
     * Convert the raw incoming arg value string to the appropriate type
     * @param tokenPosition the position of the token (in original args[])
     * @param rawValue the raw string arg value
     * @param argument the argument for which the value was found
     * @param specifiedArgumentName the name by which the argument was specified
     * @return the converted value
     * @throws BadArgumentException if the conversion fails
     */
    T convert(int tokenPosition, String rawValue, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException;
}