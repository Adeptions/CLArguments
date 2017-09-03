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

public class ArgsParsingExceptionTests extends TestCase {
	private static final String testMessage = "This is a test exception message";

	public void testArgsParsingExceptionConstructor() throws Exception {
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED);
		assertNull(argsParsingException.getMessage());
		assertNull(argsParsingException.getCause());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
	}

	public void testArgsParsingExceptionConstructor2() throws Exception {
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertNull(argsParsingException.getCause());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
	}

	public void testArgsParsingExceptionConstructor3() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, cause);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor4() throws Exception {
		ArgName specifiedArgName = ArgName.parseFromSpacedArgToken(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
	}

	public void testArgsParsingExceptionConstructor5() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgName specifiedArgName = ArgName.parseFromSpacedArgToken(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, cause, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
		assertEquals(cause, argsParsingException.getCause());
	}
}