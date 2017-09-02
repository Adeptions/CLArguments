package com.adeptions.arguments;

import junit.framework.TestCase;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

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

	public void testFooEnumArgumentDefinitionArgumentCreate() throws Exception {
		ArgumentDefinition<FooEnum> fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription).addDefaultValue(FooEnum.FOO_2);
		Argument<FooEnum> fooEnumIArgument = fooEnumArgumentDefinition.createArgumentInstance();
		assertFalse(".isSpecified() should be false",
				fooEnumIArgument.isSpecified());
		assertEquals(FooEnum.FOO_2, fooEnumIArgument.getValue());
	}

	public void testFooEnumArgumentDefinitionArgumentCreate2() throws Exception {
		FooEnumArgumentDefinition fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription);
		Argument<FooEnum> fooEnumIArgument = fooEnumArgumentDefinition.createArgumentInstance();
		assertFalse(".isSpecified() should be false",
				fooEnumIArgument.isSpecified());
		assertNull(fooEnumIArgument.getValue());
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
		public Argument<FooEnum> createArgumentInstance() {
			return new FooEnumArgument(this);
		}
	}

	private class FooEnumArgument extends AbstractArgument<FooEnum> implements Argument<FooEnum> {
		public FooEnumArgument(ArgumentDefinition<FooEnum> definition) {
			super(definition);
		}

		@Override
		public void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException {
			FooEnum setValue = null;
			for (FooEnum value: FooEnum.values()) {
				if (value.name().equals(rawValue)) {
					setValue = value;
				}
			}
			if (setValue != null) {
				values.add(setValue);
				specified = true;
			} else {
				throw new ArgParsingException(INVALID_VALUE, "Value '" + rawValue + "' is not permissible (for argument '" + specifiedArgName.getDisplayName() + "')", this, specifiedArgName);
			}
		}
	}

	private enum FooEnum {
		FOO_1,
		FOO_2
	}

}
