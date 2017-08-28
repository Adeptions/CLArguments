package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentDefinitionsCreateArgumentInstanceTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";
	private static final String defaultStringValue = "foo";
	private static final Double defaultDoubleValue = 1.0;
	private static final Integer defaultIntegerValue = 1;

	public void testStringArgumentDefinitionCreateArgumentInstance() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		IArgument createdArgument = stringArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof StringArgument",
				createdArgument instanceof StringArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}

	public void testStringArgumentDefinitionCreateArgumentInstance2() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		stringArgumentDefinition.setDefaultValue(defaultStringValue);
		IArgument createdArgument = stringArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof StringArgument",
				createdArgument instanceof StringArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertEquals(defaultStringValue, createdArgument.getValue());
	}

	public void testIntegerArgumentDefinitionCreateArgumentInstance() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
		IArgument createdArgument = integerArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof IntegerArgument",
				createdArgument instanceof IntegerArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}

	public void testIntegerArgumentDefinitionCreateArgumentInstance2() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
		integerArgumentDefinition.setDefaultValue(defaultIntegerValue);
		IArgument createdArgument = integerArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof IntegerArgument",
				createdArgument instanceof IntegerArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertEquals(defaultIntegerValue, createdArgument.getValue());
	}

	public void testDoubleArgumentDefinitionCreateArgumentInstance() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
		IArgument createdArgument = doubleArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument shoule be instanceof DoubleArgument",
				createdArgument instanceof DoubleArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}

	public void testDoubleArgumentDefinitionCreateArgumentInstance2() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
		doubleArgumentDefinition.setDefaultValue(defaultDoubleValue);
		IArgument createdArgument = doubleArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof DoubleArgument",
				createdArgument instanceof DoubleArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertEquals(defaultDoubleValue, createdArgument.getValue());
	}

	public void testBooleanArgumentDefinitionCreateArgumentInstance() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
		IArgument createdArgument = booleanArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof BooleanArgument",
				createdArgument instanceof BooleanArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}

	public void testBooleanArgumentDefinitionCreateArgumentInstance2() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
		booleanArgumentDefinition.setDefaultValue(Boolean.TRUE);
		IArgument createdArgument = booleanArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof BooleanArgument",
				createdArgument instanceof BooleanArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertEquals(Boolean.TRUE, createdArgument.getValue());
	}

	public void testFlagArgumentDefinitionCreateArgumentInstance() throws Exception {
		FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription);
		IArgument createdArgument = flagArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof BooleanArgument",
				createdArgument instanceof FlagArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}

	public void testFlagArgumentDefinitionCreateArgumentInstance2() throws Exception {
		FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription);
		flagArgumentDefinition.setDefaultValue(Boolean.TRUE);
		IArgument createdArgument = flagArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof BooleanArgument",
				createdArgument instanceof FlagArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertEquals(Boolean.TRUE, createdArgument.getValue());
	}

	public void testInformationalArgumentDefinitionCreateArgumentInstance() throws Exception {
		InformationalArgumentDefinition informationalArgumentDefinition = new InformationalArgumentDefinition(testName, testDescription);
		IArgument createdArgument = informationalArgumentDefinition.createArgumentInstance();
		assertTrue("Created argument should be instanceof BooleanArgument",
				createdArgument instanceof InformationalArgument);
		assertFalse(".isSpecified() should be false",
				createdArgument.isSpecified());
		assertNull(createdArgument.getValue());
	}
}
