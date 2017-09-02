package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgsParsing_CharBetweenTests extends TestCase {
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

	private static final ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions('=', ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, null);

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
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertEquals(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}
}
