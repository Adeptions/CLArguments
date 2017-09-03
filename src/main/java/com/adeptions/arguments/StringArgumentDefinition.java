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

public class StringArgumentDefinition extends AbstractArgumentDefinition<String> implements ArgumentDefinition<String> {
	/**
	 * Instantiates a new instance of a StringArgumentDefinition
	 * @param name the name of the argument
	 * @param description the description of the argument
	 */
	public StringArgumentDefinition(String name, String description) {
		super(ArgumentDefinitionType.VALUED, name, description);
		addValueFormat("string");
	}

	/**
	 * Instantiates a new instance of a StringArgumentDefinition
	 * @param names the names of the argument
	 * @param description the description of the argument
	 */
	public StringArgumentDefinition(String[] names, String description) {
		super(ArgumentDefinitionType.VALUED, names, description);
		addValueFormat("string");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertRawValue(int tokenPosition, String rawValue, Argument<String> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueConverter != null) {
			return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgName);
		}
		// no conversion necessary...
		return rawValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Argument<String> createArgumentInstance(Arguments parentArguments) {
		return new StringArgument(parentArguments, this);
	}
}