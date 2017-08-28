package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgNameTests extends TestCase {
	public void testArgNameSpaced() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName = ArgName.parseSpacedArgNameFromArg("-foo", argsParsingOptions);
		assertNull(argName.exception);
		assertEquals("foo", argName.name);
		assertEquals("foo", argName.getName());
		assertEquals(true, argName.isOk());
		assertEquals("-foo", argName.getDisplayName());
		assertEquals("-foo", argName.getRaw());
	}

	public void testArgNameSpaced2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, '-', '+');
		ArgName argName = ArgName.parseSpacedArgNameFromArg("-foo+", argsParsingOptions);
		assertNull(argName.exception);
		assertEquals("foo", argName.name);
		assertEquals("foo", argName.getName());
		assertEquals(true, argName.isOk());
		assertEquals("-foo+", argName.getDisplayName());
		assertEquals("-foo+", argName.getRaw());
	}

	public void testArgNameSpaced3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
		assertNotNull(argName.exception);
		assertEquals(false, argName.isOk());
	}

	public void testArgNameSpaced4() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, '-', '+');
		ArgName argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
		assertNotNull(argName.exception);
		assertEquals(false, argName.isOk());
	}

	public void testArgNameSpaced5() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, '-', '+');
		ArgName argName = ArgName.parseSpacedArgNameFromArg("-foo", argsParsingOptions);
		assertNotNull(argName.exception);
		assertEquals(false, argName.isOk());
	}

	public void testArgNameSpaced6() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setThrowImmediateParsingExceptions(true);
		boolean failed = false;
		ArgName argName;
		try {
			argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
		} catch (ArgsParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgNameSpaced7() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setThrowImmediateParsingExceptions(true);
		boolean failed = false;
		ArgName argName;
		try {
			argName = ArgName.parseSpacedArgNameFromArg(null, argsParsingOptions);
		} catch (ArgsParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgNameSpaced8() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName = ArgName.parseSpacedArgNameFromArg(null, argsParsingOptions);
		assertNotNull(argName.exception);
		assertEquals(false, argName.isOk());
	}
}
