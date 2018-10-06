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

import com.adeptions.clarguments.ArgName;
import com.adeptions.clarguments.BadArgException;
import com.adeptions.clarguments.arguments.*;

/**
 * Interface for validating values
 */
@FunctionalInterface
public interface ArgumentValueValidator<T> {
    /**
     * Validates the value
     * @param tokenPosition the position of the token (in original args[])
     * @param value the value to be validated
     * @param argument the argument for which the value was found
     * @param specifiedArgName the name by which the argument was specified
     * @return the validated value (normally the same as was passed in)
     * @throws BadArgException if the validation fails
     */
    T validate(int tokenPosition, T value, Argument argument, ArgName specifiedArgName) throws BadArgException;
}