package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgsParsingOptionsTests extends TestCase {
	private static final Character alternateCharBetweenArgNameAndValue = '=';
	private static final Character alternateArgNamePrefix = '[';
	private static final Character alternateArgNameSuffix = ']';

	public void testArgsParsingOptionsConstructor() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertEquals(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
		assertFalse("Default .isThrowImmediateParsingExceptions() should be false",
				argsParsingOptions.isThrowImmediateParsingExceptions());
	}

	public void testArgsParsingOptionsConstructor2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
		assertEquals(alternateCharBetweenArgNameAndValue, argsParsingOptions.getCharacterBetweenArgNameAndValue());
		assertFalse("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
	}

	public void testArgsParsingOptionsConstructor3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, null, null);
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getArgNamePrefix());
		assertNull(argsParsingOptions.getArgNameSuffix());
	}

	public void testArgsParsingOptionsConstructor4() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(null, alternateArgNamePrefix, alternateArgNameSuffix);
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
		assertEquals(alternateArgNamePrefix, argsParsingOptions.getArgNamePrefix());
		assertEquals(alternateArgNameSuffix, argsParsingOptions.getArgNameSuffix());
	}

	public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		argsParsingOptions.setCharacterBetweenArgNameAndValue(alternateCharBetweenArgNameAndValue);
		assertFalse(".isSpaceBetweenArgNameAndValue() should now be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
	}

	public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
		assertFalse(".isSpaceBetweenArgNameAndValue() should be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		argsParsingOptions.setCharacterBetweenArgNameAndValue(null);
		assertTrue(".isSpaceBetweenArgNameAndValue() should now be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
	}

	public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue3() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
		assertFalse(".isSpaceBetweenArgNameAndValue() should be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		argsParsingOptions.setCharacterBetweenArgNameAndValue(' ');
		assertTrue(".isSpaceBetweenArgNameAndValue() should now be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}

	public void testArgsParsingOptionsSetSpaceBtweenArgNameAndValue() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
		assertFalse(".isSpaceBetweenArgNameAndValue() should be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		argsParsingOptions.setSpaceBetweenArgNameAndValue(true);
		assertTrue(".isSpaceBetweenArgNameAndValue() should now be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertNull(argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}

	public void testArgsParsingOptionsSetSpaceBtweenArgNameAndValue2() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertTrue("Default .isSpaceBetweenArgNameAndValue() should be true",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		argsParsingOptions.setSpaceBetweenArgNameAndValue(false);
		assertFalse(".isSpaceBetweenArgNameAndValue() should now be false",
				argsParsingOptions.isSpaceBetweenArgNameAndValue());
		assertEquals(ArgsParsingOptions.DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE, argsParsingOptions.getCharacterBetweenArgNameAndValue());
	}

	public void testArgsParsingOptionsSetArgNamePrefix() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertEquals(ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX, argsParsingOptions.getArgNamePrefix());
		argsParsingOptions.setArgNamePrefix(alternateArgNamePrefix);
		assertEquals(alternateArgNamePrefix, argsParsingOptions.getArgNamePrefix());
	}

	public void testArgsParsingOptionsSetArgNameSuffix() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertNull(argsParsingOptions.getArgNameSuffix());
		argsParsingOptions.setArgNameSuffix(alternateArgNameSuffix);
		assertEquals(alternateArgNameSuffix, argsParsingOptions.getArgNameSuffix());
	}

	public void testArgsParsingOptionsSetThrowImmediate() throws Exception {
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		assertFalse("Default .isThrowImmediateParsingExceptions() should be false",
				argsParsingOptions.isThrowImmediateParsingExceptions());
		argsParsingOptions.setThrowImmediateParsingExceptions(true);
		assertTrue(".isThrowImmediateParsingExceptions() should now be true",
				argsParsingOptions.isThrowImmediateParsingExceptions());
	}
}
