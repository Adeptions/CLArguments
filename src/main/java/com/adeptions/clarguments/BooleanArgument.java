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
package com.adeptions.clarguments;

public class BooleanArgument extends AbstractArgument<Boolean> implements Argument<Boolean> {
	/**
	 * Constructs a BooleanArgument with the specified parent arguments and argument definition
	 * @param parentArguments the arguments to which the argument belongs
	 * @param definition the definition of the argument
	 */
	public BooleanArgument(Arguments parentArguments, ArgumentDefinition<Boolean> definition) {
		super(parentArguments, definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRawValue(int tokenPosition, String rawValue, ArgName specifiedArgName) throws ArgParsingException {
		values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgName), this, specifiedArgName));
		specified = true;
	}
}