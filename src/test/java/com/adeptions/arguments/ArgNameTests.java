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

public class ArgNameTests extends TestCase {
	public void testArgNameSpaced() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName = ArgName.parseFromSpacedArgToken(-1, "-foo", argsParsingOptions);
		assertEquals("foo", argName.getName());
		assertEquals("-foo", argName.getDisplayName());
		assertEquals("-foo", argName.getRawToken());
	}

	public void testArgNameSpaced2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, "-", "+");
		ArgName argName = ArgName.parseFromSpacedArgToken(-1, "-foo+", argsParsingOptions);
		assertEquals("foo", argName.getName());
		assertEquals("-foo+", argName.getDisplayName());
		assertEquals("-foo+", argName.getRawToken());
	}

	public void testArgNameSpaced3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName;
		boolean failed = false;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, "foo", argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception",
				failed);
	}

	public void testArgNameSpaced4() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, "-", "+");
		ArgName argName;
		boolean failed = false;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, "foo", argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception",
				failed);
	}

	public void testArgNameSpaced5() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, "-", "+");
		ArgName argName;
		boolean failed = false;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, "-foo", argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception",
				failed);
	}

	public void testArgNameSpaced6() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		boolean failed = false;
		ArgName argName;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, "foo", argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception",
				failed);
	}

	public void testArgNameSpaced7() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new ArgsParsingExceptionHandler() {
			@Override
			public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
				throw argsParsingException;
			}
		});
		boolean failed = false;
		ArgName argName;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, null, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgNameSpaced8() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName;
		boolean failed = false;
		try {
			argName = ArgName.parseFromSpacedArgToken(-1, null, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}
}