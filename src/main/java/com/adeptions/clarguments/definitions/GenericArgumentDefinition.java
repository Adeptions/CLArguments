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
package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;
import com.adeptions.clarguments.converters.*;

/**
 * Represents the definition for a generic typed valued argument
 */
public class GenericArgumentDefinition<T> extends AbstractArgumentDefinition<T> implements ArgumentDefinition<T> {
	/**
	 * Constructs a GenericArgumentDefinition with the specified name, description and converter
	 * @param name the name of the argument
	 * @param description the description of the argument
	 * @param valueConverter the converter to be used to convert raw arg string values to the appropriate type
	 */
	public GenericArgumentDefinition(String name, String description, ArgumentValueConverter<T> valueConverter) {
		super(ArgumentDefinitionType.VALUED, name, description);
		if (valueConverter == null) {
			throw new IllegalArgumentException("Argument 'valueConverter' cannot be null on GenericArgumentDefinition constructor");
		}
		this.valueConverter = valueConverter;
	}

	/**
	 * Constructs a GenericArgumentDefinition with the specified names, description and converter
	 * @param names the names of the argument
	 * @param description the description of the argument
	 * @param valueConverter the converter to be used to convert raw arg string values to the appropriate type
	 */
	public GenericArgumentDefinition(String[] names, String description, ArgumentValueConverter<T> valueConverter) {
		super(ArgumentDefinitionType.VALUED, names, description);
		if (valueConverter == null) {
			throw new IllegalArgumentException("Argument 'valueConverter' cannot be null on GenericArgumentDefinition constructor");
		}
		this.valueConverter = valueConverter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T convertRawValue(int tokenPosition, String rawValue, Argument<T> argument, ArgName specifiedArgName) throws BadArgException {
		return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Argument<T> createArgumentInstance(Arguments parentArguments) {
		return new GenericArgument<T>(parentArguments, this);
	}
}
