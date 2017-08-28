package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentDefinitionExceptionTests extends TestCase {
	private static final String testMessage = "This is a test exception message";

	public void testArgumentDefinitionExceptionIsRuntimeException() throws Exception {
		ArgumentDefinitionException ade = new ArgumentDefinitionException();
		boolean caught = false;
		try {
			throw ade;
		} catch (RuntimeException rte) {
			caught = true;
		}
		assertTrue("Exception should have been caught", caught);
	}

	public void testArgumentDefinitionExceptionConstructor2() throws Exception {
		ArgumentDefinitionException ade = new ArgumentDefinitionException(testMessage);
		assertEquals(testMessage, ade.getMessage());
	}

	public void testArgumentDefinitionExceptionConstructor3() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgumentDefinitionException ade = new ArgumentDefinitionException(testMessage, cause);
		assertEquals(testMessage, ade.getMessage());
		assertEquals(cause, ade.getCause());
	}

	public void testArgumentDefinitionExceptionConstructor4() throws Exception {
		IllegalArgumentException cause = new IllegalArgumentException();
		ArgumentDefinitionException ade = new ArgumentDefinitionException(cause);
		assertEquals(cause, ade.getCause());
	}
}
