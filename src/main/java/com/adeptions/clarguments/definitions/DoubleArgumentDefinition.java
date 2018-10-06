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

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

/**
 * Represents the definition for a Double valued argument
 */
public class DoubleArgumentDefinition extends AbstractArgumentDefinition<Double> implements ArgumentDefinition<Double> {
    /**
     * Constructs a DoubleArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public DoubleArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("number");
    }

    /**
     * Constructs a DoubleArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public DoubleArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("number");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double convertRawValue(int tokenPosition, String rawValue, Argument<Double> argument, ArgName specifiedArgName) throws BadArgException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgName);
        }
        try {
            Double result = Double.parseDouble(rawValue);
            if (result.isNaN()) {
                throw new NumberFormatException("Raw value was 'NaN'");
            }
            return result;
        } catch (NullPointerException nullPointerException) {
            throw new BadArgException(MISSING_VALUE, tokenPosition, "Value missing", nullPointerException, argument, specifiedArgName);
        } catch (NumberFormatException numberFormatException) {
            throw new BadArgException(INVALID_VALUE, tokenPosition, "Value '" + rawValue + "' is not a valid number", numberFormatException, argument, specifiedArgName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<Double> createArgumentInstance(Arguments parentArguments) {
        return new DoubleArgument(parentArguments, this);
    }
}