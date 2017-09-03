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
	 * @param specifiedArgName the name by which the argument was specified
	 * @return the converted value
	 * @throws ArgParsingException if the conversion fails
	 */
	T convert(int tokenPosition, String rawValue, Argument argument, ArgName specifiedArgName) throws ArgParsingException;
}