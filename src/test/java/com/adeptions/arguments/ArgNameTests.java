package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgNameTests extends TestCase {
	public void testArgNameSpaced() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName = ArgName.parseSpacedArgNameFromArg("-foo", argsParsingOptions);
		assertEquals("foo", argName.getName());
		assertEquals("-foo", argName.getDisplayName());
		assertEquals("-foo", argName.getRaw());
	}

	public void testArgNameSpaced2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, "-", "+");
		ArgName argName = ArgName.parseSpacedArgNameFromArg("-foo+", argsParsingOptions);
		assertEquals("foo", argName.getName());
		assertEquals("-foo+", argName.getDisplayName());
		assertEquals("-foo+", argName.getRaw());
	}

	public void testArgNameSpaced3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		ArgName argName;
		boolean failed = false;
		try {
			argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
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
			argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
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
			argName = ArgName.parseSpacedArgNameFromArg("-foo", argsParsingOptions);
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
			argName = ArgName.parseSpacedArgNameFromArg("foo", argsParsingOptions);
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
			argName = ArgName.parseSpacedArgNameFromArg(null, argsParsingOptions);
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
			argName = ArgName.parseSpacedArgNameFromArg(null, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}
}
