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

import static com.adeptions.arguments.ArgParsingExceptionReason.INVALID_VALUE;

public class IntegerArgumentDefinition extends AbstractArgumentDefinition<Integer> implements ArgumentDefinition<Integer> {
	/**
	 * Constructs an IntegerArgumentDefinition with the specified name and description
	 * @param name the name of the argument
	 * @param description the description of the argument
	 */
	public IntegerArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addValueFormat("integer");
	}

	/**
	 * Constructs an IntegerArgumentDefinition with the specified names and description
	 * @param names the names of the argument
	 * @param description the description of the argument
	 */
	public IntegerArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addValueFormat("integer");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer convertRawValue(String rawValue, Argument<Integer> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		try {
			return Integer.parseInt(rawValue, 10);
		} catch (NumberFormatException numberFormatException) {
			throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not a valid integer (for argument '" + specifiedArgName.getDisplayName() + "')", numberFormatException, argument, specifiedArgName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Argument<Integer> createArgumentInstance(Arguments parentArguments) {
		return new IntegerArgument(parentArguments, this);
	}
}