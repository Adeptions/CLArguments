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
package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.definitions.*;
import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * This class provides a skeletal implementation of <tt>Argument</tt> with default basic implementations of common
 * methods.
 *
 * Extend this class to create entirely new typed arguments (or simply extend/override behaviour of existing
 * concrete classes (e.g. BooleanArgument, DoubleArgument, FlagArgument, InformationalArgument, IntegerArgument or
 * StringArgument)
 *
 * This class specifically does NOT implement the setRawValue() method - which must be implemented in concrete classes.
 *
 * @param <T> the type of the argument value
 */
public abstract class AbstractArgument<T> implements Argument<T> {
	protected Arguments parentArguments;
	protected ArgumentDefinition<T> definition;
	protected boolean specified;
	protected List<T> values = new ArrayList<T>();
	protected List<String> invalidValues = new ArrayList<String>();

	/**
	 * Basic abstract constructor
	 * @param definition the definition of the argument
	 */
	public AbstractArgument(@NotNull Arguments parentArguments, @NotNull ArgumentDefinition<T> definition) {
		if (parentArguments == null) {
			throw new IllegalArgumentException("Argument 'parentArguments' cannot be null for AbstractArgument constructor");
		}
		this.parentArguments = parentArguments;
		if (definition == null) {
			throw new IllegalArgumentException("Argument 'definition' cannot be null for AbstractArgument constructor");
		}
		this.definition = definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> getDefinition() {
		return definition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSpecified() {
		return specified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSpecified() {
		specified = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue() {
		if (specified) {
			return values.size() > 0 ? values.get(0) : null;
		} else if (definition.hasDefaultValue()) {
			return definition.getDefaultValue();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getAllValues() {
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasInvalidValues() {
		return invalidValues.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getInvalidValues() {
		return invalidValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInvalidValue(String invalidValue) {
		invalidValues.add(invalidValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Arguments getParentArguments() {
		return parentArguments;
	}
}