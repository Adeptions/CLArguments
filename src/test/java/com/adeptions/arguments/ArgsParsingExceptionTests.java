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
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgParsingException argsParsingException = new ArgParsingException(cause);
		assertEquals(cause.getClass().getName(), argsParsingException.getMessage());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor5() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, argument);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(argument, argsParsingException.getArgument());
	}

	public void testArgsParsingExceptionConstructor7() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgName specifiedArgName = ArgName.createFromSpacedArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, argument, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(argument, argsParsingException.getArgument());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
	}

	public void testArgsParsingExceptionConstructor8() throws Exception {
		ArgName specifiedArgName = ArgName.createFromSpacedArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
	}

	public void testArgsParsingExceptionConstructor9() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgName specifiedArgName = ArgName.createFromSpacedArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, cause, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
		assertEquals(cause, argsParsingException.getCause());
	}

	public void testArgsParsingExceptionConstructor10() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgName specifiedArgName = ArgName.createFromSpacedArg(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgsParsingOptions());
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition("foo", "foo");
		BooleanArgument argument = new BooleanArgument(booleanArgumentDefinition);
		ArgParsingException argsParsingException = new ArgParsingException(ArgParsingExceptionReason.UNDEFINED, testMessage, cause, argument, specifiedArgName);
		assertEquals(testMessage, argsParsingException.getMessage());
		assertEquals(ArgParsingExceptionReason.UNDEFINED, argsParsingException.getReason());
		assertEquals(specifiedArgName, argsParsingException.getSpecifiedArgName());
		assertEquals(argument, argsParsingException.getArgument());
		assertEquals(cause, argsParsingException.getCause());
	}
}
