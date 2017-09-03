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
package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentDefinitionConstructionTests extends TestCase {
	private static final String testName = "test";
	private static final String testShortName = "t";
	private static final String testDescription = "This is an argument definition description";
	private static final String defaultStringValue = "foo";
	private static final Double defaultDoubleValue = 1.0;
	private static final Integer defaultIntegerValue = 1;

	public void testStringArgumentDefinitionConstructor() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
		assertEquals(testName, stringArgumentDefinition.getName());
		assertEquals(testDescription, stringArgumentDefinition.getDescription());
		assertEquals(ArgumentDefinitionType.VALUED, stringArgumentDefinition.getType());
		assertEquals(0, stringArgumentDefinition.getAlternativeNames().size());
		assertFalse("Default .hasDefaultValue() should be false",
				stringArgumentDefinition.hasDefaultValue());
		assertEquals(null, stringArgumentDefinition.getDefaultValue());
		assertTrue(".isValued() should be true",
				stringArgumentDefinition.isValued());
		assertFalse(".isFlag() should be false",
				stringArgumentDefinition.isFlag());
		assertFalse(".isInformational() should be false",
				stringArgumentDefinition.isInformational());
		assertFalse("Default .isMandatory() should be false",
				stringArgumentDefinition.isMandatory());
	}

	public void testArgumentDefinitionConstructorNotNullType() throws Exception {
		BadArgumentDefinition badArgumentDefinition;
		boolean failed = false;
		try {
			badArgumentDefinition = new BadArgumentDefinition(null, testName, testDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgumentDefinitionConstructorNotNullType2() throws Exception {
		BadArgumentDefinition badArgumentDefinition;
		boolean failed = false;
		try {
			badArgumentDefinition = new BadArgumentDefinition(null, new String[] {testName, testShortName}, testDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionConstructorNotNullName() throws Exception {
		StringArgumentDefinition stringArgumentDefinition;
		String nullName = null;
		boolean failed = false;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(nullName, testDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionConstructorNotNullName2() throws Exception {
		StringArgumentDefinition stringArgumentDefinition;
		boolean failed = false;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, null}, testDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionConstructorNotEmptyNames() throws Exception {
		StringArgumentDefinition stringArgumentDefinition;
		boolean failed = false;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(new String[] {}, testDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionConstructorNotNullDescription() throws Exception {
		StringArgumentDefinition stringArgumentDefinition;
		String nullDescription = null;
		boolean failed = false;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(testName, nullDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionConstructorNotNullDescription2() throws Exception {
		StringArgumentDefinition stringArgumentDefinition;
		String nullDescription = null;
		boolean failed = false;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, testShortName}, nullDescription);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testIntegerArgumentDefinitionConstructor() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, integerArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				integerArgumentDefinition.isValued());
	}

	public void testIntegerArgumentDefinitionConstructor2() throws Exception {
		IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, integerArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				integerArgumentDefinition.isValued());
		assertEquals(1, integerArgumentDefinition.getAlternativeNames().size());
	}

	public void testIntegerArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<Integer> integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription).addDefaultValue(defaultIntegerValue).makeMandatory();
		assertTrue(".hasDefaultValue() should be true",
				integerArgumentDefinition.hasDefaultValue());
		assertEquals(defaultIntegerValue, integerArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				integerArgumentDefinition.isMandatory());
	}

	public void testDoubleArgumentDefinitionConstructor() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, doubleArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				doubleArgumentDefinition.isValued());
	}

	public void testDoubleArgumentDefinitionConstructor2() throws Exception {
		DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, doubleArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				doubleArgumentDefinition.isValued());
		assertEquals(1, doubleArgumentDefinition.getAlternativeNames().size());
	}

	public void testDoubleArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<Double> doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription).addDefaultValue(defaultDoubleValue).makeMandatory();
		assertTrue(".hasDefaultValue() should be true",
				doubleArgumentDefinition.hasDefaultValue());
		assertEquals(defaultDoubleValue, doubleArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				doubleArgumentDefinition.isMandatory());
	}

	public void testBooleanArgumentDefinitionConstructor() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, booleanArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				booleanArgumentDefinition.isValued());
	}

	public void testBooleanArgumentDefinitionConstructor2() throws Exception {
		BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(ArgumentDefinitionType.VALUED, booleanArgumentDefinition.getType());
		assertTrue(".isValued() should be true",
				booleanArgumentDefinition.isValued());
		assertEquals(1, booleanArgumentDefinition.getAlternativeNames().size());
	}

	public void testBooleanArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<Boolean> booleanIArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription).addDefaultValue(Boolean.TRUE).makeMandatory();
		assertTrue(".hasDefaultValue() should be true",
				booleanIArgumentDefinition.hasDefaultValue());
		assertEquals(Boolean.TRUE, booleanIArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				booleanIArgumentDefinition.isMandatory());
	}

	public void testFlagArgumentDefinitionConstructor() throws Exception {
		FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription);
		assertEquals(ArgumentDefinitionType.FLAG, flagArgumentDefinition.getType());
		assertTrue(".isFlag() should be true",
				flagArgumentDefinition.isFlag());
		assertFalse(".isValued() should be false",
				flagArgumentDefinition.isValued());
		assertFalse(".isInformational() should be false",
				flagArgumentDefinition.isInformational());
	}

	public void testFlagArgumentDefinitionConstructor2() throws Exception {
		FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(ArgumentDefinitionType.FLAG, flagArgumentDefinition.getType());
		assertTrue(".isFlag() should be true",
				flagArgumentDefinition.isFlag());
		assertEquals(1, flagArgumentDefinition.getAlternativeNames().size());
	}

	public void testFlagArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<Boolean> flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription).addDefaultValue(true).makeMandatory();
		assertTrue(".hasDefaultValue() should be true",
				flagArgumentDefinition.hasDefaultValue());
		assertEquals(Boolean.TRUE, flagArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				flagArgumentDefinition.isMandatory());
	}

	public void testInformationalArgumentDefinitionConstructor() throws Exception {
		InformationalArgumentDefinition informationalArgumentDefinition = new InformationalArgumentDefinition(testName, testDescription);
		assertEquals(ArgumentDefinitionType.INFORMATIONAL, informationalArgumentDefinition.getType());
		assertTrue(".isInformational() should be true",
				informationalArgumentDefinition.isInformational());
		assertFalse(".isFlag() should be false",
				informationalArgumentDefinition.isFlag());
		assertFalse(".isValued() should be false",
				informationalArgumentDefinition.isValued());
	}

	public void testInformationalArgumentDefinitionConstructor2() throws Exception {
		InformationalArgumentDefinition informationalArgumentDefinition = new InformationalArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(ArgumentDefinitionType.INFORMATIONAL, informationalArgumentDefinition.getType());
		assertTrue(".isInformational() should be true",
				informationalArgumentDefinition.isInformational());
		assertEquals(1, informationalArgumentDefinition.getAlternativeNames().size());
	}

	public void testInformationalArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<Boolean> informationalArgumentDefinition = new InformationalArgumentDefinition(testName, testDescription).addDefaultValue(true).makeMandatory();
		assertTrue("Default .hasDefaultValue() should be true",
				informationalArgumentDefinition.hasDefaultValue());
		assertNotNull(informationalArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				informationalArgumentDefinition.isMandatory());
	}

	public void testStringArgumentDefinitionMultiNamedConstructor() throws Exception {
		StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, testShortName}, testDescription);
		assertEquals(testName, stringArgumentDefinition.getName());
		assertEquals(1, stringArgumentDefinition.getAlternativeNames().size());
		assertTrue("Alternative names should contain alternative name",
				stringArgumentDefinition.getAlternativeNames().contains(testShortName));
	}

	public void testStringArgumentDefinitionFailsWithMultiSameNamedConstructor() throws Exception {
		boolean failed = false;
		StringArgumentDefinition stringArgumentDefinition;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(new String[]{testName, testName}, testDescription);
		} catch (ArgumentDefinitionException ade) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionFailsWithMultiSameNamed2Constructor() throws Exception {
		boolean failed = false;
		StringArgumentDefinition stringArgumentDefinition;
		try {
			stringArgumentDefinition = new StringArgumentDefinition(new String[]{testName, testShortName, testShortName}, testDescription);
		} catch (ArgumentDefinitionException ade) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testStringArgumentDefinitionChainedConstruction() throws Exception {
		ArgumentDefinition<String> stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription).addDefaultValue(defaultStringValue);
		assertTrue(".hasDefaultValue() should be true",
				stringArgumentDefinition.hasDefaultValue());
		assertEquals(defaultStringValue, stringArgumentDefinition.getDefaultValue());
	}

	public void testStringArgumentDefinitionChained2Construction() throws Exception {
		ArgumentDefinition<String> stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription).addDefaultValue(defaultStringValue);
		assertTrue(".hasDefaultValue() should be true",
				stringArgumentDefinition.hasDefaultValue());
		assertEquals(defaultStringValue, stringArgumentDefinition.getDefaultValue());
	}

	public void testStringArgumentDefinitionChained3Construction() throws Exception {
		ArgumentDefinition<String> stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription).addDefaultValue(defaultStringValue).makeMandatory();
		assertTrue(".hasDefaultValue() should be true",
				stringArgumentDefinition.hasDefaultValue());
		assertEquals(defaultStringValue, stringArgumentDefinition.getDefaultValue());
		assertTrue(".isMandatory() should be true",
				stringArgumentDefinition.isMandatory());
	}

	private class BadArgumentDefinition extends AbstractArgumentDefinition<Boolean> implements ArgumentDefinition<Boolean> {
		BadArgumentDefinition(ArgumentDefinitionType type, String name, String description) {
			super(type, name, description);
		}

		BadArgumentDefinition(ArgumentDefinitionType type, String[] names, String description) {
			super(type, names, description);
		}

		@Override
		public boolean hasDefaultValue() {
			return false;
		}

		@Override
		public Boolean getDefaultValue() {
			return null;
		}

		@Override
		public Boolean convertRawValue(String rawValue, Argument<Boolean> argument, ArgName specifiedArgName) throws ArgParsingException {
			return null;
		}

		@Override
		public ArgumentDefinition<Boolean> addDefaultValue(Boolean value) {
			return null;
		}

		@Override
		public Argument<Boolean> createArgumentInstance(Arguments parentArguments) {
			return null;
		}
	}
}