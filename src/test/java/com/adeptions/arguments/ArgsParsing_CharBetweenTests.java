package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgsParsing_CharBetweenTests extends TestCase {
	private static final String testArgumentName1 = "string1";
	private static final String testAlternateArgumentName1 = "s1";
	private static final String testArgumentValue1 = "foo";
	private static final String testArgumentName2 = "integer2";
	private static final String testAlternateArgumentName2 = "i2";
	private static final Integer testArgumentValue2 = 123;
	private static final String testArgumentName3 = "double3";
	private static final Double testArgumentValue3 = 123.4;
	private static final String testArgumentName4 = "flag4";
	private static final String testArgumentName5 = "help";
	private static final String testAlternateArgumentName5 = "h";
	private static final String testArgumentName6 = "boolean6";
	private static final Boolean testArgumentValue6 = true;

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

	private static final Character argNameAndValueSeparator = '=';
	private static final ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(argNameAndValueSeparator, ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, null);

	public void testArgsParsingEmptyArgs() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[0], argsParsingOptions);
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(0, arguments.getSpecifiedArguments().size());
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
		assertFalse("Default .isSpaceBetweenArgNameAndValue() should be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertEquals(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
		assertEquals(argNameAndValueSeparator, argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}

	public void testArgsParsingAllArgs() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		Arguments arguments = argumentDefinitions.parseArgs(new String[] {
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1 + argNameAndValueSeparator + testArgumentValue1,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2 + argNameAndValueSeparator + testArgumentValue2,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3 + argNameAndValueSeparator + testArgumentValue3,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName5,
				ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName6 + argNameAndValueSeparator + testArgumentValue6
		}, argsParsingOptions);
		assertEquals(argumentDefinitions.size(), arguments.size());
		assertEquals(argumentDefinitions.size(), arguments.getSpecifiedArguments().size());
		assertFalse(".hasParsingExceptions() should be false",
				arguments.hasParsingExceptions());
		assertTrue("Argument '" + testArgumentName1 + "' should be specified",
				arguments.get(testArgumentName1).isSpecified()
		);
		assertEquals(testArgumentValue1, arguments.get(testArgumentName1).getValue());
		assertTrue("Argument '" + testArgumentName2 + "' should be specified",
				arguments.get(testArgumentName2).isSpecified()
		);
		assertEquals(testArgumentValue2, arguments.get(testArgumentName2).getValue());
		assertTrue("Argument '" + testArgumentName3 + "' should be specified",
				arguments.get(testArgumentName3).isSpecified()
		);
		assertEquals(testArgumentValue3, arguments.get(testArgumentName3).getValue());
		assertTrue("Argument '" + testArgumentName4 + "' should be specified",
				arguments.get(testArgumentName4).isSpecified()
		);
		assertTrue("Argument '" + testArgumentName5 + "' should be specified",
				arguments.get(testArgumentName5).isSpecified()
		);
		assertTrue("Argument '" + testArgumentName6 + "' should be specified",
				arguments.get(testArgumentName6).isSpecified()
		);
		assertEquals(testArgumentValue6, arguments.get(testArgumentName6).getValue());
	}
}
