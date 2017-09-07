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
package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;

import java.util.*;

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

/**
 * Utility value converter for converting raw values to boolean
 * The various true/false accepted values are specified in constructor
 */
public class MultiValueBooleanConverter implements ArgumentValueConverter<Boolean> {
	private boolean allowNulls;
	private Boolean assumeNullValue;
	private Map<String,Boolean> trueAndFalses = new HashMap<String,Boolean>();
	private String advisoryString;

	/**
	 * Constructs an MultiValueBooleanConverter with default values and acceptable raw values
	 * All boolean values are accepted - i.e.
	 *   "true"|"false", "yes"|"no", "on"|"off" and "1"|"0"
	 */
	public MultiValueBooleanConverter() {
		this.allowNulls = allowNulls;
		initAcceptableValues(new String[] {"true|false", "yes|no", "on|off", "1|0"});
	}

	/**
	 * Constructs an MultiValueBooleanConverter with specified acceptable raw values
	 * @param trueFalsePairs the strings that define the acceptable true|false values
	 *                       in the format "true|false" (the vertical bar is required and must have a string either side)
	 * All boolean values are accepted - i.e.
	 *   "true"|"false", "yes"|"no", "on"|"off" and "1"|"0"
	 */
	public MultiValueBooleanConverter(String... trueFalsePairs) {
		initAcceptableValues(trueFalsePairs);
	}

	/**
	 * Constructs an MultiValueBooleanConverter with specified acceptable raw values
	 * @param allowNulls whether nulls should be allowed
	 * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
	 * @param trueFalsePairs the strings that define the acceptable true|false values
	 *                       in the format "true|false" (the vertical bar is required and must have a string either side)
	 * All boolean values are accepted - i.e.
	 *   "true"|"false", "yes"|"no", "on"|"off" and "1"|"0"
	 */
	public MultiValueBooleanConverter(boolean allowNulls, Boolean assumeNullValue, String... trueFalsePairs) {
		this.allowNulls = allowNulls;
		this.assumeNullValue = assumeNullValue;
		initAcceptableValues(trueFalsePairs);
	}

	private void initAcceptableValues(String[] trueFalsePairs) {
		StringBuilder advisoryStringBuilder = new StringBuilder();
		for (int i = 0, imax = trueFalsePairs.length; i < imax; i++) {
			String[] valuePair = (trueFalsePairs[i] != null ? trueFalsePairs[i].split("\\|") : new String[0]);
			if (valuePair.length != 2 || valuePair[0].isEmpty() || valuePair[1].isEmpty() || valuePair[0].equals(valuePair[1])) {
				throw new IllegalArgumentException("True|false pair strings passed to MultiValueBooleanConverter constructor must be in the format \"true|false\"");
			}
			advisoryStringBuilder.append((i == 0 ? "" : (i == (imax - 1) ? " or " : ", ")))
					.append("\"").append(valuePair[0]).append("\"")
					.append("|\"").append(valuePair[1]).append("\"");
			trueAndFalses.put(valuePair[0], Boolean.TRUE);
			trueAndFalses.put(valuePair[1], Boolean.FALSE);
		}
		advisoryString = advisoryStringBuilder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean convert(int tokenPosition, String rawValue, Argument argument, ArgName specifiedArgName) throws BadArgException {
		if (allowNulls && rawValue == null) {
			return assumeNullValue;
		}
		if (!trueAndFalses.containsKey(rawValue)) {
			throw new BadArgException(INVALID_VALUE, tokenPosition, "Value \"" + rawValue + "\" is invalid - must be " + advisoryString, argument, specifiedArgName);
		}
		return trueAndFalses.get(rawValue);
	}
}