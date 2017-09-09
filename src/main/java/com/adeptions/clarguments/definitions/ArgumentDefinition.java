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
import com.adeptions.clarguments.validators.*;

import java.util.*;

/**
 * Interface for argument definitions
 *
 * @param <T> is the final type of the argument value
 */
public interface ArgumentDefinition<T> {
	/**
	 * Gets the argument type (VALUED, FLAG or INFORMATIONAL) of the argument definition
	 * @return the argument type (VALUED, FLAG or INFORMATIONAL) of the argument definition
	 */
	ArgumentDefinitionType getType();

	/**
	 * Gets the name of the defined argument
	 * @return the name of the defined argument
	 */
	String getName();

	/**
	 * Gets the alternative names of the defined argument
	 * @return the alternative names of the defined argument
	 */
	Set<String> getAlternativeNames();

	/**
	 * Gets the display name of the defined argument (with prefix and suffix added)
	 * @param argsParsingOptions the args parsing options that denote any prefix or suffix
	 * @return the display name of the defined argument
	 */
	String getDisplayName(ArgsParsingOptions argsParsingOptions);

	/**
	 * Gets the description of the defined argument
	 * @return the description of the defined argument
	 */
	String getDescription();

	/**
	 * Gets the value format of the defined argument (used to display help)
	 * @return the value format of the defined argument
	 */
	String getValueFormat();

	/**
	 * Gets the help string for the argument definition according to the specified args parsing options
	 * @param argsParsingOptions the args parsing options that denote argument format (prefix, suffix, etc.)
	 * @return the help string for the argument definition
	 */
	String getHelp(ArgsParsingOptions argsParsingOptions);

	/**
	 * Gets whether the argument definition is a FLAG argument
	 * @return whether the argument definition is a FLAG argument
	 */
	boolean isFlag();

	/**
	 * Gets whether the argument definition is a VALUED argument
	 * @return whether the argument definition is a VALUED argument
	 */
	boolean isValued();

	/**
	 * Gets whether the argument definition is a INFORMATIONAL argument
	 * @return whether the argument definition is a INFORMATIONAL argument
	 */
	boolean isInformational();

	/**
	 * Gets whether the argument is mandatory
	 * @return whether the argument is mandatory
	 */
	boolean isMandatory();

	/**
	 * Gets whether the argument definition has a default value
	 * @return whether the argument definition has a default value
	 */
	boolean hasDefaultValue();

	/**
	 * Gets the default value of the argument definition
	 * @return the default value of the argument definition
	 */
	T getDefaultValue();

	/**
	 * Gets the value converter for the argument definition
	 * @return the value converter for the argument definition
	 */
	ArgumentValueConverter<T> getValueConverter();

	/**
	 * Gets the list of value validators for the argument definition
	 * @return the list of value validators for the argument definition
	 */
	List<ArgumentValueValidator<T>> getValueValidators();

	/**
	 * Converts the raw incoming arg value (string) to the appropriate type
	 * @param tokenPosition the position of the arg in srgs[]
	 * @param rawValue the raw incoming arg value
	 * @param argument the argument
	 * @param specifiedArgName the specified arg name
	 * @return the converted value
	 * @throws BadArgException if the conversion fails (i.e. the raw value is not a valid representation of expected type)
	 */
	T convertRawValue(int tokenPosition, String rawValue, Argument<T> argument, ArgName specifiedArgName) throws BadArgException;

	/**
	 * Validates the converted arg value
	 * @param tokenPosition the position of the arg in srgs[]
	 * @param value the value to be checked
	 * @param argument the argument
	 * @param specifiedArgName the specified arg name
	 * @return the validated value
	 * @throws BadArgException if the validation fails (e.g. the value was out of range)
	 */
	T validateValue(int tokenPosition, T value, Argument<T> argument, ArgName specifiedArgName) throws BadArgException;

	/**
	 * Chaining method for adding a value format
	 * @param valueFormat the argument value format
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> addValueFormat(String valueFormat);

	/**
	 * Chaining method for adding a value converter
	 * @param valueConverter the argument value converter
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> addValueConverter(ArgumentValueConverter<T> valueConverter);

	/**
	 * Chaining method for adding the value validator
	 * (overwrites any previously added validator - see addAdditionalValueValidator() to add multiple validators)
	 * @param valueValidator the argument value validator to be added
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> addValueValidator(ArgumentValueValidator<T> valueValidator);

	/**
	 * Chaining method for adding additional value validator
	 * @param valueValidator the additional argument value validator to be added
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> addAdditionalValueValidator(ArgumentValueValidator<T> valueValidator);

	/**
	 * Chaining method for making the argument mandatory
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> makeMandatory();

	/**
	 * Chaining method for adding a default value to the argument definition
	 * @param value the default value
	 * @return a reference to this object
	 */
	ArgumentDefinition<T> addDefaultValue(T value);

	/**
	 * Creates an appropriate instance of an Argument for this argument definition
	 * @param parentArguments the parent arguments for the generated argument
	 * @return the created instance of an argument
	 */
	Argument<T> createArgumentInstance(Arguments parentArguments);
}