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

import junit.framework.TestCase;

public class BadArgExceptionTests extends TestCase {
    private static final String testMessage = "This is a test exception message";

    public void testBadArgExceptionConstructor() throws Exception {
        BadArgException badArgException = new BadArgException(PredefinedBadArgReasons.UNDEFINED, -1);
        assertNull(badArgException.getMessage());
        assertNull(badArgException.getCause());
        assertEquals(PredefinedBadArgReasons.UNDEFINED, badArgException.getReason());
    }

    public void testBadArgExceptionConstructor2() throws Exception {
        BadArgException badArgException = new BadArgException(PredefinedBadArgReasons.UNDEFINED, -1, testMessage);
        assertEquals(testMessage, badArgException.getMessage());
        assertNull(badArgException.getCause());
        assertEquals(PredefinedBadArgReasons.UNDEFINED, badArgException.getReason());
    }

    public void testBadArgExceptionConstructor3() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgException badArgException = new BadArgException(PredefinedBadArgReasons.UNDEFINED, -1, testMessage, cause);
        assertEquals(testMessage, badArgException.getMessage());
        assertEquals(PredefinedBadArgReasons.UNDEFINED, badArgException.getReason());
        assertEquals(cause, badArgException.getCause());
    }

    public void testBadArgExceptionConstructor4() throws Exception {
        ArgName specifiedArgName = ArgName.parseFromSpacedArgToken(-1, ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
        BadArgException badArgException = new BadArgException(PredefinedBadArgReasons.UNDEFINED, -1, testMessage, specifiedArgName);
        assertEquals(testMessage, badArgException.getMessage());
        assertEquals(PredefinedBadArgReasons.UNDEFINED, badArgException.getReason());
        assertEquals(specifiedArgName, badArgException.getSpecifiedArgName());
    }

    public void testBadArgExceptionConstructor5() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        ArgName specifiedArgName = ArgName.parseFromSpacedArgToken(-1, ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
        BadArgException badArgException = new BadArgException(PredefinedBadArgReasons.UNDEFINED, -1, testMessage, cause, specifiedArgName);
        assertEquals(testMessage, badArgException.getMessage());
        assertEquals(PredefinedBadArgReasons.UNDEFINED, badArgException.getReason());
        assertEquals(specifiedArgName, badArgException.getSpecifiedArgName());
        assertEquals(cause, badArgException.getCause());
    }
}