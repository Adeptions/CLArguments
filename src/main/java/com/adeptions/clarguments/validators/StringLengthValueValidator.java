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
package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;

import static com.adeptions.clarguments.ArgParsingExceptionReason.*;

/**
 * Utility value validator for checking a string value is within length constraints (minimum and/or maximum)
 */
public class StringLengthValueValidator implements ArgumentValueValidator<String> {
	private Integer minLength;
	private Integer maxLength;

	/**
	 * Constructs a StringLengthValueValidator with the specified minimum and maximum length values
	 * @param minLength the minimum length of the string (null for no minimum enforced)
	 * @param maxLength the maximum length of the string (null for no maximum enforced)
	 */
	public StringLengthValueValidator(Integer minLength, Integer maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validate(int tokenPosition, String value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
		if (value != null) {
			if (minLength != null && value.length() < minLength) {
				throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Supplied value length must be greater than or equal to " + minLength, argument, specifiedArgName);
			}
			if (maxLength != null && value.length() > maxLength) {
				throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Supplied value length must be less than or equal to " + maxLength, argument, specifiedArgName);
			}
		} else if (minLength != null) {
			throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Supplied value length must be greater than or equal to " + minLength, argument, specifiedArgName);
		}
		return value;
	}
}