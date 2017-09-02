package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgsParsing_SpaceBetweenTests extends TestCase {
	private static final String testArgumentName1 = "string1";
	private static final String testAlternateArgumentName1 = "s1";
	private static final String testArgumentName2 = "integer2";
	private static final String testAlternateArgumentName2 = "i2";
	private static final String testArgumentName3 = "double3";
	private static final String testArgumentName4 = "flag4";
	private static final String testArgumentName5 = "help";
	private static final String testAlternateArgumentName5 = "h";
	private static final String testArgumentName6 = "boolean6";

	private static final String[] allArgumentNames = new String[] {
			testArgumentName1, testAlternateArgumentName1,
			testArgumentName2, testAlternateArgumentName2,
			testArgumentName3,
			testArgumentName4,
			testArgumentName5
	};
	private static final String testDescription = "This is an argument definition description";

	private static final ArgumentDefinition argumentDefinition1 = new StringArgumentDefinition(new String[] {testArgumentName1, testAlternateArgumentName1}, testDescription).makeMandatory();
	private static final ArgumentDefinition argumentDefinition2 = new IntegerArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName2}, testDescription);
	private static final ArgumentDefinition argumentDefinition3 = new DoubleArgumentDefinition(testArgumentName3, testDescription);
	private static final ArgumentDefinition argumentDefinition4 = new FlagArgumentDefinition(testArgumentName4, testDescription);
	private static final ArgumentDefinition argumentDefinition5 = new InformationalArgumentDefinition(new String[] {testArgumentName5, testAlternateArgumentName5}, testDescription);
	private static final ArgumentDefinition argumentDefinition6 = new BooleanArgumentDefinition(testArgumentName6, testDescription);

	public void testArgsParsingEmptyArgs() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[0]);
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertFalse("No arguments were specified", arguments.anySpecified());
		assertEquals(argumentDefinitions, arguments.getArgumentDefinitions());
		assertTrue(".hasParsingExceptions() should be true (there was one missing mandatory argument)",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
		for (String argumentName: allArgumentNames) {
			assertNotNull(arguments.get(argumentName));
			assertFalse("Argument .isSpecified() should be false",
					arguments.get(argumentName).isSpecified());
		}
		assertFalse(".hasSpecifiedInformationals() should be false",
				arguments.hasSpecifiedInformationals());
		assertEquals(0, arguments.getSpecifiedInformationals().size());
		assertTrue(".hasMissingMandatories() should be true",
				arguments.hasMissingMandatories());
		assertEquals(1, arguments.getMissingMandatories().size());
		ArgsParsingOptions argsParsingOptions = arguments.getArgsParsingOptions();
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertEquals(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}

	public void testArgsParsingManyArgs() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1, "foo",
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2, "123",
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3, "123.4",
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testAlternateArgumentName5,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName6, "true"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(argumentDefinitions.size(), arguments.getSpecifiedArguments().size());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName1).isSpecified());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName2).isSpecified());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName3).isSpecified());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName4).isSpecified());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName5).isSpecified());
		assertTrue(".isSpecified() should be true",
				arguments.get(testArgumentName6).isSpecified());
		assertTrue(".hasSpecifiedInformationals() should be true",
				arguments.hasSpecifiedInformationals());
		assertEquals("foo", (String)arguments.get(testArgumentName1).getValue());
		assertEquals((Integer)123, (Integer)arguments.get(testArgumentName2).getValue());
		assertEquals((Double)123.4, (Double)arguments.get(testArgumentName3).getValue());
		assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName4).getValue());
		assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName5).getValue());
		assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName6).getValue());
	}

	public void testArgsParsingOneMandatoryStringArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1,
				"foo"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument<String> argument = arguments.get(testArgumentName1);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
		assertEquals("foo", argument.getValue());
	}

	public void testArgsParsingOneMandatoryStringMissingValueArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		Argument<String> argument = arguments.get(testArgumentName1);
		assertNotNull(argument);
		assertFalse("Argument .isSpecified() should be false",
				argument.isSpecified());
		assertTrue("There should be parsing exceptions",
				arguments.hasParsingExceptions());
		assertEquals(2, arguments.getParsingExceptions().size());
		assertTrue(".hasMissingMandatories() should be true",
				arguments.hasMissingMandatories());
		assertEquals(1, arguments.getMissingMandatories().size());
	}

	public void testArgsParsingMissingMandatoryArgFailsImmediatelt() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new ArgsParsingExceptionHandler() {
			@Override
			public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
				throw argsParsingException;
			}
		});
		Arguments arguments;
		boolean failed = false;
		try {
			arguments = argumentDefinitions.parseArgs(new String[] {}, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have failed",
				failed);
	}

	public void testArgsParsingOneIntegerArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
				"123"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName2);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
		Integer argValue = (Integer)argument.getValue();
		assertEquals((Integer)123, argValue);
	}

	public void testArgsParsingOneDoublerArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition3
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
				"123.4"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName3);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
		Double argValue = (Double)argument.getValue();
		assertEquals(123.4, argValue);
	}

	public void testArgsParsingOneFlagArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition4
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName4);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
		Boolean argValue = (Boolean)argument.getValue();
		assertEquals(Boolean.TRUE, argValue);
	}

	public void testArgsParsingOneInformationalArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName5
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName5);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
		Boolean argValue = (Boolean)argument.getValue();
		assertEquals(Boolean.TRUE, argValue);
		assertTrue(".hasSpecifiedInformationals() should be true",
				arguments.hasSpecifiedInformationals());
		assertEquals(1, arguments.getSpecifiedInformationals().size());
	}

	public void testArgsParsingOneBadIntegerArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
				"xxx"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName2);
		assertNotNull(argument);
		assertFalse("Argument .isSpecified() should be false",
				argument.isSpecified());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
	}

	public void testArgsParsingOneBadIntegerArgFailsImmediately() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2
		);
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new ArgsParsingExceptionHandler() {
			@Override
			public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
				throw argsParsingException;
			}
		});
		boolean failed = false;
		Arguments arguments;
		try {
			arguments = argumentDefinitions.parseArgs(new String[] {
					ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
					"xxx"
			}, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have failed", failed);
	}

	public void testArgsParsingMissingMandatoryFlagArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				new FlagArgumentDefinition(testArgumentName4, testDescription).makeMandatory()
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4,
		});
		assertFalse(".hasMissingMandatories() should be false",
				arguments.hasMissingMandatories());
	}

	public void testArgsParsingOneBadDoublerArg() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition3
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
				"xxx"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName3);
		assertNotNull(argument);
		assertFalse("Argument .isSpecified() should be false",
				argument.isSpecified());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
	}

	public void testArgsParsingCheckMissingValueFollowedByArgName() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition3,
				argumentDefinition4
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(1, arguments.getSpecifiedArguments().size());
		Argument argument = arguments.get(testArgumentName3);
		assertNotNull(argument);
		assertFalse("Argument .isSpecified() should be false",
				argument.isSpecified());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
		argument = arguments.get(testArgumentName4);
		assertNotNull(argument);
		assertTrue("Argument .isSpecified() should be true",
				argument.isSpecified());
	}

	public void testArgsParsingUnknownArgName() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "badArgName"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
		assertTrue(".hasUnknownArgs() should be true",
				arguments.hasUnknownArgNames());
		assertEquals(1, arguments.getUnknownArgNames().size());
	}

	public void testArgsParsingUnknownArgValue() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				"badArgName"
		});
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
		assertTrue(".hasUnknownArgValues() should be true",
				arguments.hasUnknownArgValues());
		assertEquals(1, arguments.getUnknownArgValues().size());
	}

	public void testArgsParsingUnknownArgValue2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				"badArgName"
		}, new ArgsParsingOptions(null, null, null));
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
		assertTrue(".hasParsingExceptions() should be true",
				arguments.hasParsingExceptions());
		assertEquals(1, arguments.getParsingExceptions().size());
		assertTrue(".hasUnknownArgs() should be true",
				arguments.hasUnknownArgNames());
		assertEquals(1, arguments.getUnknownArgNames().size());
	}

	public void testArgsParsingUnknownArgValue3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new ArgsParsingExceptionHandler() {
			@Override
			public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
				throw argsParsingException;
			}
		});
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		boolean failed = false;
		Arguments arguments;
		try {
			arguments = argumentDefinitions.parseArgs(new String[] {
					ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "badArgName"
			}, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have failed",
				failed);
	}

	public void testArgsParsingUnknownArgValue4() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new ArgsParsingExceptionHandler() {
			@Override
			public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
				throw argsParsingException;
			}
		});
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		boolean failed = false;
		Arguments arguments;
		try {
			arguments = argumentDefinitions.parseArgs(new String[] {
					"badArgName"
			}, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			failed = true;
		}
		assertTrue("Should have failed",
				failed);
	}
}
