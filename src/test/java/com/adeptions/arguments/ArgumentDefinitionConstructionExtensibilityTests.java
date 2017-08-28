package com.adeptions.arguments;

import junit.framework.TestCase;

public class ArgumentDefinitionConstructionExtensibilityTests extends TestCase {
	private static final String testName = "test";
	private static final String testDescription = "This is an argument definition description";

	public void testFooEnumArgumentDefinitionConstructor() throws Exception {
		FooEnumArgumentDefinition fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription);
		assertEquals(testName, fooEnumArgumentDefinition.getName());
		assertEquals(testDescription, fooEnumArgumentDefinition.getDescription());
		assertEquals(ArgumentDefinitionType.VALUED, fooEnumArgumentDefinition.getType());
		assertEquals(false, fooEnumArgumentDefinition.areMultiplesAllowed());
		assertEquals(0, fooEnumArgumentDefinition.getAlternativeNames().size());
		assertFalse("Default .hasDefaultValue() should be false",
				fooEnumArgumentDefinition.hasDefaultValue());
		assertEquals(null, fooEnumArgumentDefinition.getDefaultValue());
		assertFalse("Default .areMultiplesAllowed() should be false",
				fooEnumArgumentDefinition.areMultiplesAllowed());
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
		IArgumentDefinition<FooEnum> fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription).setDefaultValue(FooEnum.FOO_2);
		assertTrue(".hasDefaultValue() should be true",
				fooEnumArgumentDefinition.hasDefaultValue());
		assertEquals(FooEnum.FOO_2, fooEnumArgumentDefinition.getDefaultValue());
	}

	public void testFooEnumArgumentDefinitionArgumentCreate() throws Exception {
		IArgumentDefinition<FooEnum> fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription).setDefaultValue(FooEnum.FOO_2);
		IArgument<FooEnum> fooEnumIArgument = fooEnumArgumentDefinition.createArgumentInstance();
		assertFalse(".isSpecified() should be false",
				fooEnumIArgument.isSpecified());
		assertEquals(FooEnum.FOO_2, fooEnumIArgument.getValue());
	}

	public void testFooEnumArgumentDefinitionArgumentCreate2() throws Exception {
		FooEnumArgumentDefinition fooEnumArgumentDefinition = new FooEnumArgumentDefinition(testName, testDescription);
		IArgument<FooEnum> fooEnumIArgument = fooEnumArgumentDefinition.createArgumentInstance();
		assertFalse(".isSpecified() should be false",
				fooEnumIArgument.isSpecified());
		assertNull(fooEnumIArgument.getValue());
	}

	private class FooEnumArgumentDefinition extends AbstractArgumentDefinition<FooEnum> implements IArgumentDefinition<FooEnum> {
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
		public IArgumentDefinition<FooEnum> setDefaultValue(FooEnum defaultValue) {
			hasDefaultValue = true;
			this.defaultValue = defaultValue;
			return this;
		}

		@Override
		public IArgument<FooEnum> createArgumentInstance() {
			return new FooEnumArgument(this);
		}
	}

	private class FooEnumArgument extends AbstractArgument<FooEnum> implements IArgument<FooEnum> {
		public FooEnumArgument(IArgumentDefinition<FooEnum> definition) {
			super(definition);
		}

		@Override
		public void setRawValue(ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
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
				throw new ArgsParsingException("Value '" + rawValue + "' is not permissible (for argument '" + specifiedArgName.displayName + "')", this, specifiedArgName);
			}
		}
	}

	private enum FooEnum {
		FOO_1,
		FOO_2
	}

}
