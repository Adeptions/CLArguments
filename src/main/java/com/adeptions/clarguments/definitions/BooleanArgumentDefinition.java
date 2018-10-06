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
 * Represents the definition for a Boolean valued argument
 */
public class BooleanArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
    /**
     * Constructs a BooleanArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public BooleanArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("true|false");
    }

    /**
     * Constructs a BooleanArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public BooleanArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("true|false");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convertRawValue(int tokenPosition, String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws BadArgException {
        if (valueConverter == null) {
            valueConverter = new TrueFalseBooleanConverter();
        }
        return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<Boolean> createArgumentInstance(Arguments parentArguments) {
        return new BooleanArgument(parentArguments, this);
    }
}