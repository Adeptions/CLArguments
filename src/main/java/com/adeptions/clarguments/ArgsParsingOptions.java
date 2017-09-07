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

/**
 * The options to be used when parsing args
 */
public class ArgsParsingOptions {
	public static final String DEFAULT_ARG_NAME_PREFIX = "-";
	public static final Character DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE = ':';
	private static final Character SPACE = ' ';
	static final BadArgExceptionsHandler DEFAULT_ARGS_PARSING_EXCEPTION_HANDLER = new BadArgExceptionsHandler() {
		@Override
		public BadArgException handle(BadArgException badArgException) throws BadArgException {
			return badArgException;
		}
	};

	protected boolean spaceBetweenArgNameAndValue;
	protected Character characterBetweenArgNameAndValue;
	protected String argNamePrefix;
	protected String argNameSuffix;
	protected BadArgExceptionsHandler badArgExceptionsHandler = DEFAULT_ARGS_PARSING_EXCEPTION_HANDLER;

	/**
	 * Constructs an ArgsParsingOptions with default options
	 *
	 * Default options are:
	 *   * spaced args
	 *   * arg name prefix is "-" (@see com.adeptions.arguments.ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX)
	 */
	public ArgsParsingOptions() {
		spaceBetweenArgNameAndValue = true;
		argNamePrefix = DEFAULT_ARG_NAME_PREFIX;
	}

	/**
	 * Constructs an ArgsParsingOptions with the specified arg name/value separator, arg name prefix and suffix
	 * @param characterBetweenArgNameAndValue the character between the arg name and value
	 *                                        (specify as null for spaced args)
	 * @param argNamePrefix the expected prefix for arg names (or null if no prefix expected)
	 * @param argNameSuffix the expected suffix for arg names (or null if no suffix expected)
	 */
	public ArgsParsingOptions(Character characterBetweenArgNameAndValue,
							  String argNamePrefix,
							  String argNameSuffix) {
		setCharacterBetweenArgNameAndValue(characterBetweenArgNameAndValue);
		setArgNamePrefix(argNamePrefix);
		setArgNameSuffix(argNameSuffix);
	}

	/**
	 * Gets whether spaced style args are to be parsed
	 * @return whether spaced style args are to be parsed
	 */
	public boolean isSpaceBetweenArgNameAndValue() {
		return spaceBetweenArgNameAndValue;
	}

	/**
	 * Sets whether spaced style args are to be parsed
	 * @param spaceBetweenArgNameAndValue whether spaced style args are to be parsed
	 *                                    (when false and no character between yet specified then the character between
	 *                                     is defaulted to ':')
	 */
	public void setSpaceBetweenArgNameAndValue(boolean spaceBetweenArgNameAndValue) {
		this.spaceBetweenArgNameAndValue = spaceBetweenArgNameAndValue;
		if (this.spaceBetweenArgNameAndValue) {
			characterBetweenArgNameAndValue = null;
		} else if (characterBetweenArgNameAndValue == null) {
			characterBetweenArgNameAndValue = DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE;
		}
	}

	/**
	 * Gets the character expected between arg name and value
	 * @return the character expected between arg name and value (null for spaced style args)
	 */
	public Character getCharacterBetweenArgNameAndValue() {
		return characterBetweenArgNameAndValue;
	}

	/**
	 * Sets the expected character between arg name and value
	 * @param characterBetweenArgNameAndValue the character between arg name and value
	 *                                        (if null specified then spaced args style is assumed)
	 */
	public void setCharacterBetweenArgNameAndValue(Character characterBetweenArgNameAndValue) {
		this.characterBetweenArgNameAndValue = (SPACE.equals(characterBetweenArgNameAndValue) ? null : characterBetweenArgNameAndValue);
		spaceBetweenArgNameAndValue = (this.characterBetweenArgNameAndValue == null);
	}

	/**
	 * Gets the expected arg name prefix
	 * @return the expected arg name prefix
	 */
	public String getArgNamePrefix() {
		return argNamePrefix;
	}

	/**
	 * Sets the expected arg name prefix
	 * @param argNamePrefix the expected arg name prefix (or null if no prefix expected)
	 */
	public void setArgNamePrefix(String argNamePrefix) {
		this.argNamePrefix = argNamePrefix == null || argNamePrefix.isEmpty() ? null : argNamePrefix;
	}

	/**
	 * Gets the expected arg name suffix
	 * @return the expected arg name suffix
	 */
	public String getArgNameSuffix() {
		return argNameSuffix;
	}

	/**
	 * Sets the expected arg name suffix
	 * @param argNameSuffix the expected arg name suffix (or null if no suffix expected)
	 */
	public void setArgNameSuffix(String argNameSuffix) {
		this.argNameSuffix = argNameSuffix == null || argNameSuffix.isEmpty() ? null : argNameSuffix;
	}

	/**
	 * Gets the args parsing exception handler
	 * @return the args parsing exception handler
	 */
	public BadArgExceptionsHandler getBadArgExceptionsHandler() {
		return badArgExceptionsHandler;
	}

	/**
	 * Sets the bad arg exception handler
	 * @param badArgExceptionsHandler the args parsing exception handler (if null is specified the default
	 *                                    handler is used - which returns exceptions rather than throwing)
	 */
	public void setBadArgsExceptionHandler(BadArgExceptionsHandler badArgExceptionsHandler) {
		if (badArgExceptionsHandler == null) {
			this.badArgExceptionsHandler = DEFAULT_ARGS_PARSING_EXCEPTION_HANDLER;
		} else {
			this.badArgExceptionsHandler = badArgExceptionsHandler;
		}
	}
}