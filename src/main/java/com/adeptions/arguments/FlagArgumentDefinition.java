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

public class FlagArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
	/**
	 * Constructs a FlagArgumentDefinition with the specified name and description
	 * @param name the name of the argument
	 * @param description the description of the argument
	 */
	public FlagArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.FLAG, name, description);
	}

	/**
	 * Constructs a FlagArgumentDefinition with the specified names and description
	 * @param names the names of the argument
	 * @param description the description of the argument
	 */
	public FlagArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.FLAG, names, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean convertRawValue(String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(rawValue, argument, specifiedArgName);
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FlagArgument createArgumentInstance(Arguments parentArguments) {
		return new FlagArgument(parentArguments, this);
	}
}