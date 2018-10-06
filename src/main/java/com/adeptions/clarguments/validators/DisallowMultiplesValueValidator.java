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

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

public class DisallowMultiplesValueValidator implements ArgumentValueValidator {
    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate(int tokenPosition, Object value, Argument argument, ArgName specifiedArgName) throws BadArgException {
        if (argument.wasSeen()) {
            throw new BadArgException(MULTIPLE_ARGUMENT_NOT_ALLOWED, tokenPosition, "Argument '" + specifiedArgName.getDisplayName() + "' cannot be specified more than once", argument, specifiedArgName);
        }
        return value;
    }
}