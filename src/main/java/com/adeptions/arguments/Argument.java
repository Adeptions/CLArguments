/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.arguments;

import java.util.*;

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
	 * Gets the value found for the argument
	 * If the argument was not specified, this method should return the argument definition default value (if specified) or
	 * simply return null.
	 * If the argument was found multiple times, this method should return the first value found
	 * @return the found value (or default or null)
	 */
	T getValue();

	/**
	 *
	 * @param rawValue the raw string value found in the command line args
	 * @param specifiedArgName the name by which the arg was specified
	 * @throws ArgParsingException if the raw value cannot be converted to the appropriate type, or the value is invalid
	 *         (or can be use to throw exception where a multiple value was found but not allowed)
	 */
	void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException;

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