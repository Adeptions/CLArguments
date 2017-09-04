/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.clarguments;

import junit.framework.TestCase;

public class ArgumentDefinitionAccessorsTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";

	public void testArgumentDefinitionSetMandatory() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertFalse("Default .isMandatory() should be false",
				stringArgumentDefinition.isMandatory());
		stringArgumentDefinition.makeMandatory();
		assertTrue(".isMandatory() should now be true",
				stringArgumentDefinition.isMandatory());
	}

	public void testArgumentDefinitionAddValidator() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertEquals(0, stringArgumentDefinition.getValueValidators().size());
		stringArgumentDefinition.addValueValidator(new ArgumentValueValidator<String>() {
			@Override
			public String validate(int tokenPosition, String value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
				return null;
			}
		});
		assertEquals(1, stringArgumentDefinition.getValueValidators().size());
	}

	public void testArgumentDefinitionAddValidators() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertEquals(0, stringArgumentDefinition.getValueValidators().size());
		ArgumentValueValidator<String> valueValidator = new ArgumentValueValidator<String>() {
			@Override
			public String validate(int tokenPosition, String value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
				return null;
			}
		};
		stringArgumentDefinition.addValueValidator(valueValidator);
		assertEquals(1, stringArgumentDefinition.getValueValidators().size());
		stringArgumentDefinition.addValueValidator(valueValidator);
		assertEquals(1, stringArgumentDefinition.getValueValidators().size());
		stringArgumentDefinition.addAdditionalValueValidator(valueValidator);
		assertEquals(2, stringArgumentDefinition.getValueValidators().size());
	}
}