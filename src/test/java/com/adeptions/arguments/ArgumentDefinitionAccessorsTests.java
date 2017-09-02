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

	public void testArgumentDefinitionSetValidator() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertNull("Default .getValueValidator() should be null",
				stringArgumentDefinition.getValueValidator());
		stringArgumentDefinition.setValueValidator(new ArgumentValueValidator() {
			@Override
			public Object validate(Object value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
				return null;
			}
		});
		assertNotNull(".getValueValidator() should now be set",
				stringArgumentDefinition.getValueValidator());
	}
}
