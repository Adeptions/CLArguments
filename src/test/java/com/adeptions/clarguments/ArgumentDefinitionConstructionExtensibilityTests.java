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

import com.adeptions.clarguments.arguments.*;
import com.adeptions.clarguments.definitions.*;
import junit.framework.TestCase;

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

public class ArgumentDefinitionConstructionExtensibilityTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";

	public void testFooEnumArgumentDefinitionConstructor() throws Exception {
		FooEnumArgumentDefinition fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription);
		assertEquals(testName, fooEnumArgumentDefinition.getName());
		assertEquals(testDescription, fooEnumArgumentDefinition.getDescription());
		assertEquals(ArgumentDefinitionType.VALUED, fooEnumArgumentDefinition.getType());
		assertEquals(0, fooEnumArgumentDefinition.getAlternativeNames().size());
		assertFalse("Default .hasDefaultValue() should be false",
				fooEnumArgumentDefinition.hasDefaultValue());
		assertEquals(null, fooEnumArgumentDefinition.getDefaultValue());
		assertTrue(".isValued() should be true",
				fooEnumArgumentDefinition.isValued());
		assertFalse(".isFlag() should be false",
				fooEnumArgumentDefinition.isFlag());
		assertFalse(".isInformational() should be false",
				fooEnumArgumentDefinition.isInformational());
		assertFalse("Default .isMandatory() should be false",
				fooEnumArgumentDefinition.isMandatory());
	}

	public void testFooEnumArgumentDefinitionConstructorWithDefault() throws Exception {
		ArgumentDefinition<FooEnum> fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription).addDefaultValue(FooEnum.FOO_2);
		assertTrue(".hasDefaultValue() should be true",
				fooEnumArgumentDefinition.hasDefaultValue());
		assertEquals(FooEnum.FOO_2, fooEnumArgumentDefinition.getDefaultValue());
	}

	private class FooEnumArgumentDefinition extends AbstractArgumentDefinition<FooEnum> implements ArgumentDefinition<FooEnum> {
		private FooEnum defaultValue;
		private boolean hasDefaultValue;

		FooEnumArgumentDefinition(String name, String description) {
			super(ArgumentDefinitionType.VALUED, name, description);
		}

		FooEnumArgumentDefinition(String[] names, String description) {
			super(ArgumentDefinitionType.VALUED, names, description);
		}

		@Override
		public boolean hasDefaultValue() {
			return hasDefaultValue;
		}

		@Override
		public FooEnum getDefaultValue() {
			return defaultValue;
		}

		@Override
		public ArgumentDefinition<FooEnum> addDefaultValue(FooEnum defaultValue) {
			hasDefaultValue = true;
			this.defaultValue = defaultValue;
			return this;
		}

		@Override
		public FooEnum convertRawValue(int tokenPosition, String rawValue, Argument<FooEnum> argument, ArgName specifiedArgName) throws BadArgException {
			FooEnum result = null;
			for (FooEnum value: FooEnum.values()) {
				if (value.name().equals(rawValue)) {
					result = value;
				}
			}
			if (result == null) {
				throw new BadArgException(INVALID_VALUE, tokenPosition, "Value '" + rawValue + "' is not permissible", argument, specifiedArgName);
			}
			return result;
		}

		@Override
		public Argument<FooEnum> createArgumentInstance(Arguments parentArguments) {
			return new FooEnumArgument(parentArguments, this);
		}
	}

	private class FooEnumArgument extends AbstractArgument<FooEnum> implements Argument<FooEnum> {
		public FooEnumArgument(Arguments parentArguments, ArgumentDefinition<FooEnum> definition) {
			super(parentArguments, definition);
		}

		@Override
		public void setRawValue(int tokenPosition, String rawValue, ArgName specifiedArgName) throws BadArgException {
			values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgName), this, specifiedArgName));
			specified = true;
		}
	}

	private enum FooEnum {
		FOO_1,
		FOO_2
	}
}