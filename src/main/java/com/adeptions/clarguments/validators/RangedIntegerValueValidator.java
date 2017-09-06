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
 * Utility value validator for checking an integer value is within a specified range
 */
public class RangedIntegerValueValidator implements ArgumentValueValidator<Integer> {
	private Integer minValue;
	private Integer maxValue;

	/**
	 * Constructs a RangedInteger with the specified minimum and maximum values
	 * @param minValue the minimum value (null for no minimum enforced)
	 * @param maxValue the maximum value (null for no maximum enforced)
	 */
	public RangedIntegerValueValidator(Integer minValue, Integer maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer validate(int tokenPosition, Integer value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
		if (value != null) {
			if (minValue != null && minValue.compareTo(value) > 0) {
				throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Supplied value " + value + " must be greater than or equal to " + minValue, argument, specifiedArgName);
			}
			if (maxValue != null && maxValue.compareTo(value) < 0) {
				throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Supplied value " + value + " must be less than or equal to " + maxValue, argument, specifiedArgName);
			}
		}
		return null;
	}
}