package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";
	private static final String testDefaultValue = "foo";
	private static final String testValue = "bar";
	private static final String testValue2 = "baz";

	public void testArgumentConstructor() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		StringArgument stringArgument = new StringArgument(stringArgumentDefinition);
		assertEquals(stringArgumentDefinition, stringArgument.getDefinition());
		assertFalse(".isSpecified() should be false",
				stringArgument.isSpecified());
		assertNull(stringArgument.getValue());
		assertEquals(0, stringArgument.getAllValues().size());
	}

	public void testArgumentDefaultValue() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		stringArgumentDefinition.setDefaultValue(testDefaultValue);
		StringArgument stringArgument = new StringArgument(stringArgumentDefinition);
		assertEquals(stringArgumentDefinition, stringArgument.getDefinition());
		assertFalse(".isSpecified() should be false",
				stringArgument.isSpecified());
		assertEquals(testDefaultValue, stringArgument.getValue());
		assertEquals(0, stringArgument.getAllValues().size());
	}

	public void testArgumentSetValue() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		StringArgument stringArgument = new StringArgument(stringArgumentDefinition);
		assertFalse(".isSpecified() should be false",
				stringArgument.isSpecified());
		assertNull(stringArgument.getValue());
		assertEquals(0, stringArgument.getAllValues().size());
		stringArgument.addValue(testValue);
		assertTrue(".isSpecified() should be true",
				stringArgument.isSpecified());
		assertEquals(testValue, stringArgument.getValue());
		assertEquals(1, stringArgument.getAllValues().size());
	}

	public void testArgumentAddValue() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		StringArgument stringArgument = new StringArgument(stringArgumentDefinition);
		assertFalse(".isSpecified() should be false",
				stringArgument.isSpecified());
		assertNull(stringArgument.getValue());
		assertEquals(0, stringArgument.getAllValues().size());
		stringArgument.addValue(testValue);
		assertTrue(".isSpecified() should be true",
				stringArgument.isSpecified());
		assertEquals(testValue, stringArgument.getValue());
		assertEquals(1, stringArgument.getAllValues().size());
		stringArgument.addValue(testValue2);
		assertEquals(testValue, stringArgument.getValue());
		assertEquals(2, stringArgument.getAllValues().size());
		assertEquals(testValue2, stringArgument.getAllValues().get(1));
	}

	public void testArgumentSetSpecified() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		StringArgument stringArgument = new StringArgument(stringArgumentDefinition);
		assertFalse(".isSpecified() should be false",
				stringArgument.isSpecified());
		stringArgument.setSpecified();
		assertTrue(".isSpecified() should now be true",
				stringArgument.isSpecified());
	}

	public void testDoubleArgumentSetRawValue() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
		DoubleArgument argument = new DoubleArgument(doubleArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "123.4");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals(123.4, argument.getValue());
	}

	public void testDoubleArgumentSetRawValueFails() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
		DoubleArgument argument = new DoubleArgument(doubleArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		boolean failed = false;
		try {
			argument.setRawValue(specifiedArgName, "xxx");
		} catch (ArgsParsingException argsParsingException) {
			failed = true;
		}
		assertTrue(".setRawValue() should have failed",
				failed);
	}

	public void testIntegerArgumentSetRawValue() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
		IntegerArgument argument = new IntegerArgument(integerArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "123");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals((Integer)123, argument.getValue());
	}

	public void testIntegerArgumentSetRawValueFailes() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
		IntegerArgument argument = new IntegerArgument(integerArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		boolean failed = false;
		try {
			argument.setRawValue(specifiedArgName, "xxx");
		} catch (ArgsParsingException argsParsingException) {
			failed = true;
		}
		assertTrue(".setRawValue() should have failed",
				failed);
	}

	public void testBooleanArgumentSetRawValue() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "true");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals(Boolean.TRUE, argument.getValue());
	}

	public void testBooleanArgumentSetRawValueFails() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		boolean failed = false;
		try {
			argument.setRawValue(specifiedArgName, "xxx");
		} catch (ArgsParsingException argsParsingException) {
			failed = true;
		}
		assertTrue(".setRawValue() should have failed",
				failed);
	}

	public void testStringArgumentSetRawValue() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		StringArgument argument = new StringArgument(stringArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "foo");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals("foo", argument.getValue());
	}

	public void testFlagArgumentSetRawValue() throws Exception {
		FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription);
		FlagArgument argument = new FlagArgument(flagArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "foo");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals(Boolean.TRUE, argument.getValue());
	}

	public void testInformationalArgumentSetRawValue() throws Exception {
		InformationalArgumentDefinition informationalArgumentDefinition = new InformationalArgumentDefinition(testName, testDescription);
		InformationalArgument argument = new InformationalArgument(informationalArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testName, new ArgsParsingOptions());
		assertFalse(".isSpecified() should be false",
				argument.isSpecified());
		argument.setRawValue(specifiedArgName, "foo");
		assertTrue(".isSpecified() should now be true",
				argument.isSpecified());
		assertEquals(Boolean.TRUE, argument.getValue());
	}
}
