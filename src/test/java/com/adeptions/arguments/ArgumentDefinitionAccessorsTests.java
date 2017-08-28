package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentDefinitionAccessorsTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";

	public void testArgumentDefinitionSetMandatory() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertFalse("Default .isMandatory() should be false",
				stringArgumentDefinition.isMandatory());
		stringArgumentDefinition.setMandatory(true);
		assertTrue(".isMandatory() should now be true",
				stringArgumentDefinition.isMandatory());
	}

	public void testArgumentDefinitionSetMultiplesAllowed() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertFalse("Default .areMultiplesAllowed() should be false",
				stringArgumentDefinition.areMultiplesAllowed());
		stringArgumentDefinition.setMultiplesAllowed(true);
		assertTrue(".areMultiplesAllowed() should now be true",
				stringArgumentDefinition.areMultiplesAllowed());
	}
}
