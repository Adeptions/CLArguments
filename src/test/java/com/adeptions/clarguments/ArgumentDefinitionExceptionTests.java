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

public class ArgumentDefinitionExceptionTests extends TestCase {
    private static final String testMessage = "This is a test exception message";

    public void testBadArgumentDefinitionExceptionIsRuntimeException() throws Exception {
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException();
        boolean caught = false;
        try {
            throw badArgumentDefinitionException;
        } catch (RuntimeException rte) {
            caught = true;
        }
        assertTrue("Exception should have been caught", caught);
    }

    public void testBadArgumentDefinitionExceptionConstructor2() throws Exception {
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(testMessage);
        assertEquals(testMessage, badArgumentDefinitionException.getMessage());
    }

    public void testBadArgumentDefinitionExceptionConstructor3() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(testMessage, cause);
        assertEquals(testMessage, badArgumentDefinitionException.getMessage());
        assertEquals(cause, badArgumentDefinitionException.getCause());
    }

    public void testBadArgumentDefinitionExceptionConstructor4() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(cause);
        assertEquals(cause, badArgumentDefinitionException.getCause());
    }
}