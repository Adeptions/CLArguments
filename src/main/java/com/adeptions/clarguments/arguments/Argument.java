package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

import java.util.List;

/**
 * Interface for actual arguments - containing value(s) found during parsing and whether the argument was found.
 *
 * @param <T> is the final type of the argument value
 */
public interface Argument<T> {
    /**
     * Gets the definition for the argument
     * @return the argument definition for the argument
     */
    ArgumentDefinition<T> getDefinition();

    /**
     * Whether the argument was specified (found) during parsing
     * @return whether the argument was specified
     */
    boolean isSpecified();

    /**
     * Denotes that the argument was specified (found) during parsing (without having to set an actual value - in the case
     * of flag or informational type arguments)
     */
    void setSpecified();

    /**
     * Whether the argument was seen (even if it was invalid) during parsing
     * (This flag is maintained so that multiple argument checkers can check if the argument was encountered
     *  at all - even if the value was invalid)
     * @return whether the argument was seen
     */
    boolean wasSeen();

    /**
     * Denotes that the argument was seen (encountered) during parsing)
     */
    void setSeen();

    /**
     * Gets the value found for the argument
     * If the argument was not specified, this method should return the argument definition default value (if specified) or
     * simply return null.
     * If the argument was found multiple times, this method should return the first value found
     * @return the found value (or default or null)
     */
    T getValue();

    /**
     * Sets the value of the argument
     * @param tokenPosition the position of the token in args[]
     * @param rawValue the raw string value found in the command line args
     * @param specifiedArgumentName the name by which the arg was specified
     * @throws BadArgumentException if the raw value cannot be converted to the appropriate type, or the value is invalid
     *         (or can be use to throw exception where a multiple value was found but not allowed)
     */
    void setRawValue(int tokenPosition, String rawValue, ArgumentName specifiedArgumentName) throws BadArgumentException;

    /**
     * Gets a list of all the values found for the argument
     * @return list of values found
     */
    List<T> getAllValues();

    /**
     * Whether any invalid values were found for the argument
     * @return whether any invalid values were found for the argument
     */
    boolean hasInvalidValues();

    /**
     * Gets a list of any invalid values found for the argument
     * @return list of invalid values
     */
    List<String> getInvalidValues();

    /**
     * Adds a found invalid value for the argument
     * @param invalidValue the invalid value found
     */
    void addInvalidValue(String invalidValue);

    /**
     * Gets the parent Arguments that contains this argument
     * @return the parent Arguments
     */
    Arguments getParentArguments();
}