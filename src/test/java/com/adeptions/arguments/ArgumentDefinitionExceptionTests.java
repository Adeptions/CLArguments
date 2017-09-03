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

import junit.framework.TestCase;

public class ArgumentDefinitionExceptionTests extends TestCase {
	private static final String testMessage = "This is a test exception message";

	public void testArgumentDefinitionExceptionIsRuntimeException() throws Exception {
		ArgumentDefinitionException ade = new ArgumentDefinitionException();
		boolean caught = false;
		try {
			throw ade;
		} catch (RuntimeException rte) {
			caught = true;
		}
		assertTrue("Exception should have been caught", caught);
	}

	public void testArgumentDefinitionExceptionConstructor2() throws Exception {
		ArgumentDefinitionException ade = new ArgumentDefinitionException(testMessage);
		assertEquals(testMessage, ade.getMessage());
	}

	public void testArgumentDefinitionExceptionConstructor3() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgumentDefinitionException ade = new ArgumentDefinitionException(testMessage, cause);
		assertEquals(testMessage, ade.getMessage());
		assertEquals(cause, ade.getCause());
	}

	public void testArgumentDefinitionExceptionConstructor4() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgumentDefinitionException ade = new ArgumentDefinitionException(cause);
		assertEquals(cause, ade.getCause());
	}
}