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
		assertNull("Default .getValidator() should be null",
				stringArgumentDefinition.getValidator());
		stringArgumentDefinition.setValidator(new IArgumentValueValidator() {
			@Override
			public Object validate(Object value, IArgument argument, ArgName specifiedArgName) throws ArgsParsingException {
				return null;
			}
		});
		assertNotNull(".getValidator() should now be set",
				stringArgumentDefinition.getValidator());
	}
}
