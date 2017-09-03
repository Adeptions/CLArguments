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

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

/**
 * Represents an arg name found during parsing
 */
public class ArgName {
	private String rawToken;
	private String name;
	private String displayName;
	private String value;

	/**
	 * Constructs an ArgName with the specified raw arg token found
	 * @param rawToken the raw arg token found
	 */
	ArgName(String rawToken) {
		this.rawToken = rawToken;
	}

	/**
	 * Gets the raw token found
	 * @return
	 */
	public String getRawToken() {
		return rawToken;
	}

	/**
	 * Gets the parsed name of the arg found
	 * @return the parsed name of the arg found (may be null if name was invalid)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the parsed display name of the arg found
	 * (i.e. with any prefix/suffix defined by the parsing options added)
	 * @return the parsed display name of the arg found (may be null if name was invalid)
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets whether the arg token also incorporated a value
	 * (NB. Only relevant when pasring args in the format name=value)
	 * @return whether the arg token also incorporated a value
	 */
	public boolean hasValue() {
		return value != null;
	}

	/**
	 * Gets the value of the arg
	 * (NB. Only relevant when pasring args in the format name=value)
	 * @return the value of the arg
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Parses a found arg token into an ArgName (used when parsing spaced args)
	 * @param rawToken the raw args token encountered
	 * @param argsParsingOptions the parsing options
	 * @return the created ArgName
	 * @throws ArgParsingException if there was a problem with the token (e.g. the name was invalid)
	 */
	static ArgName parseFromSpacedArgToken(String rawToken, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		String argNamePrefix = argsParsingOptions.getArgNamePrefix();
		String argNameSuffix = argsParsingOptions.getArgNameSuffix();
		ArgName result = new ArgName(rawToken);
		String name = rawToken;
		try {
			if (argNamePrefix != null) {
				if (name.startsWith(argNamePrefix)) {
					name = name.substring(argNamePrefix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_PREFIX, "Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if (argNameSuffix != null) {
				if (name.endsWith(argNameSuffix)) {
					name = name.substring(0, name.length() - argNameSuffix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_SUFFIX, "Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgParsingException argsParsingException) {
			throw argsParsingException;
		} catch (Exception ex) {
			throw new ArgParsingException(UNEXPECTED_EXCEPTION, "Unknown error parsing argument name", ex, result);
		}
		return result;
	}

	/**
	 * Parses a found arg token into an ArgName (used when parsing non-spaced args)
	 * @param rawToken the raw args token encountered
	 * @param argsParsingOptions the parsing options
	 * @return the created ArgName
	 * @throws ArgParsingException if there was a problem with the token (e.g. the name was invalid)
	 */
	static ArgName parseFromNonSpacedArgToken(String rawToken, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		String argNamePrefix = argsParsingOptions.getArgNamePrefix();
		String argNameSuffix = argsParsingOptions.getArgNameSuffix();
		Character argNameAndValueSeparator = argsParsingOptions.getCharacterBetweenArgNameAndValue();
		ArgName result = new ArgName(rawToken);
		String name = rawToken;
		int separatorAt;
		try {
			if (argNamePrefix != null) {
				if (name.startsWith(argNamePrefix)) {
					name = name.substring(argNamePrefix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_PREFIX, "Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if ((separatorAt = name.indexOf(argNameAndValueSeparator)) != -1) {
				result.value = name.substring(separatorAt + 1);
				name = name.substring(0, separatorAt);
			}
			if (argNameSuffix != null) {
				if (name.endsWith(argNameSuffix)) {
					name = name.substring(0, name.length() - argNameSuffix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_SUFFIX, "Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgParsingException argsParsingException) {
			throw argsParsingException;
		} catch (Exception ex) {
			throw new ArgParsingException(UNEXPECTED_EXCEPTION, "Unknown error parsing argument name", ex, result);
		}
		return result;
	}
}