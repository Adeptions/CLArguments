package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgsParsingExceptionTests extends TestCase {
	private static final String testMessage = "This is a test exception message";

	public void testArgsParsingExceptionConstructor() throws Exception {
		ArgsParsingException argsParsingException = new ArgsParsingException();
		assertNull(argsParsingException.getMessage());
		assertNull(argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor2() throws Exception {
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertNull(argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor3() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, cause);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor4() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgsParsingException argsParsingException = new ArgsParsingException(cause);
		assertEquals(cause.getClass().getName(), argsParsingException.getMessage());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor5() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, argument);
		assertEquals(argument, argsParsingException.getArgument());
	}

	public void testArgsParsingExceptionConstructor7() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, argument, specifiedArgName);
		assertEquals(argument, argsParsingException.getArgument());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
	}

	public void testArgsParsingExceptionConstructor8() throws Exception {
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, specifiedArgName);
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
	}

	public void testArgsParsingExceptionConstructor9() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, cause, specifiedArgName);
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor10() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgName specifiedArgName = ArgName.parseSpacedArgNameFromArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgsParsingException argsParsingException = new ArgsParsingException(testMessage, cause, argument, specifiedArgName);
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
		assertEquals(argument, argsParsingException.getArgument());
		assertEquals(cause, argsParsingException.getCause());
	}
}
