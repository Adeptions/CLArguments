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

import com.sun.istack.internal.NotNull;

import java.util.*;

import static com.adeptions.clarguments.ArgumentDefinitionType.*;

/**
 * This class provides a skeletal implementation of <tt>ArgumentDefinition</tt> with default basic implementations of
 * common methods.
 *
 * Extend this class to create entirely new typed arguments definitions (or simply extend/override behaviour of existing
 * concrete classes (e.g. BooleanArgumentDefinition, DoubleArgumentDefinition, FlagArgumentDefinition,
 * InformationalArgumentDefinition, IntegerArgumentDefinition or StringArgumentDefinition)
 *
 * This class specifically does NOT implement the convertRawValue() and createArgumentInstance() methods - which must be
 * implemented in concrete classes.
 *
 * @param <T> the type of the argument definition value
 */
public abstract class AbstractArgumentDefinition<T> implements ArgumentDefinition<T> {
	protected final ArgumentDefinitionType type;
	protected final String name;
	protected final String description;
	protected String format;
	protected boolean mandatory;
	protected ArgumentValueConverter<T> valueConverter;
	protected List<ArgumentValueValidator<T>> valueValidators = new ArrayList<ArgumentValueValidator<T>>();
	private Set<String> alternativeNames = new HashSet<String>();
	protected T defaultValue;

	/**
	 * Basic constructor for instantiating single name argument definition
	 *
	 * @param type the type of the argument definition (valued, flag or informational)
	 * @param name the name of the defined argument
	 * @param description the description of the argument (used in help etc.)
	 */
	public AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String name, @NotNull String description) {
		this.type = checkType(type);
		this.name = checkName(name);
		this.description = checkDescription(description);
	}

	/**
	 * Basic constructor for instantiating multiple named argument definition
	 *
	 * @param type the type of the argument definition (valued, flag or informational)
	 * @param names the names of the defined argument (the first in the array defines the primary name)
	 * @param description the description of the argument (used in help etc.)
	 */
	public AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String[] names, @NotNull String description) {
		this.type = checkType(type);
		if (names.length == 0) {
			throw new ArgumentDefinitionException("Argument definition must have at least one name");
		}
		this.name = checkName(names[0]);
		for (int n = 1, nmax = names.length; n < nmax; n++) {
			alternativeNames.add(checkAlternativeName(names[n]));
		}
		this.description = checkDescription(description);
	}

	private ArgumentDefinitionType checkType(ArgumentDefinitionType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' cannot be null on AbstractArgumentDefinition constructor");
		}
		return type;
	}

	private String checkName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' cannot be null on AbstractArgumentDefinition constructor");
		}
		return name;
	}

	private String checkAlternativeName(String alternativeName) {
		if (alternativeName == null) {
			throw new ArgumentDefinitionException("Argument definition alternative name cannot be null");
		} else if (name.equals(alternativeName) || alternativeNames.contains(alternativeName)) {
			throw new ArgumentDefinitionException("Argument definition alternative name already used");
		}
		return alternativeName;
	}

	private String checkDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Argument 'description' cannot be null on AbstractArgumentDefinition constructor");
		}
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDisplayName(ArgsParsingOptions argsParsingOptions) {
		ArgsParsingOptions useArgsParsingOptions = argsParsingOptions == null ? new ArgsParsingOptions() : argsParsingOptions;
		String argNamePrefix = argsParsingOptions.getArgNamePrefix();
		String argNameSuffix = argsParsingOptions.getArgNameSuffix();
		return (argNamePrefix != null ? "" + argNamePrefix : "") +
				name +
				(argNameSuffix != null ? "" + argNameSuffix : "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValueFormat() {
		return format;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> addValueFormat(String format) {
		this.format = format;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHelp(ArgsParsingOptions argsParsingOptions) {
		StringBuilder builder = new StringBuilder("    ").append(getDisplayName(argsParsingOptions));
		if (!argsParsingOptions.isSpaceBetweenArgNameAndValue()) {
			String format = getValueFormat();
			builder.append(argsParsingOptions.getCharacterBetweenArgNameAndValue());
			if (format != null && !format.isEmpty()) {
				builder.append(format);
			} else {
				builder.append("value");
			}
		}
		if (!isMandatory()) {
			builder.append("  [optional]");
		}
		builder.append("\n        ")
				.append(getDescription());
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T validateValue(int tokenPosition, T value, Argument<T> argument, ArgName specifiedArgName) throws ArgParsingException {
		T result = value;
		for (ArgumentValueValidator<T> valueValidator: valueValidators) {
			result = valueValidator.validate(tokenPosition, result, argument, specifiedArgName);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinitionType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFlag() {
		return type == FLAG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValued() {
		return type == VALUED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInformational() {
		return type == INFORMATIONAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentValueConverter<T> getValueConverter() {
		return valueConverter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ArgumentValueValidator<T>> getValueValidators() {
		return valueValidators;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getAlternativeNames() {
		// we cannot allow accessors to alter the internal set - so return a copy...
		return new HashSet<String>(alternativeNames);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> addValueConverter(ArgumentValueConverter<T> valueConverter) {
		this.valueConverter = valueConverter;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> addValueValidator(ArgumentValueValidator<T> valueValidator) {
		this.valueValidators.clear();
		this.valueValidators.add(valueValidator);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> addAdditionalValueValidator(ArgumentValueValidator<T> valueValidator) {
		this.valueValidators.add(valueValidator);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> makeMandatory() {
		mandatory = true;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasDefaultValue() {
		return defaultValue != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArgumentDefinition<T> addDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
}