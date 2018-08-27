package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.definitions.AbstractArgumentDefinition;
import com.adeptions.clarguments.definitions.ArgumentDefinition;
import com.adeptions.clarguments.definitions.BooleanArgumentDefinition;
import com.adeptions.clarguments.definitions.DoubleArgumentDefinition;
import com.adeptions.clarguments.definitions.FlagArgumentDefinition;
import com.adeptions.clarguments.definitions.InformationalArgumentDefinition;
import com.adeptions.clarguments.definitions.IntegerArgumentDefinition;
import com.adeptions.clarguments.definitions.StringArgumentDefinition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ArgumentDefinitionConstructionTests {
    private static final String testName = "test";
    private static final String testShortName = "t";
    private static final String testDescription = "This is an argument definition description";
    private static final String defaultStringValue = "foo";
    private static final Double defaultDoubleValue = 1.0;
    private static final Integer defaultIntegerValue = 1;

    @Test
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

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentDefinitionConstructorNotNullType() {
        BadArgumentDefinition badArgumentDefinition = new BadArgumentDefinition(null, testName, testDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentDefinitionConstructorNotNullType2() {
        BadArgumentDefinition badArgumentDefinition = new BadArgumentDefinition(null, new String[] {testName, testShortName}, testDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringArgumentDefinitionConstructorNotNullName() {
        String nullName = null;
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(nullName, testDescription);
    }

    @Test(expected = BadArgumentDefinitionException.class)
    public void testStringArgumentDefinitionConstructorNotNullName2() {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, null}, testDescription);
    }

    @Test(expected = BadArgumentDefinitionException.class)
    public void testStringArgumentDefinitionConstructorNotEmptyNames() {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[] {}, testDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringArgumentDefinitionConstructorNotNullDescription() {
        String nullDescription = null;
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, nullDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringArgumentDefinitionConstructorNotNullDescription2() throws Exception {
        String nullDescription = null;
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, testShortName}, nullDescription);
    }

    @Test
    public void testIntegerArgumentDefinitionConstructor() throws Exception {
        IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, integerArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                integerArgumentDefinition.isValued());
    }

    @Test
    public void testIntegerArgumentDefinitionConstructor2() throws Exception {
        IntegerArgumentDefinition integerArgumentDefinition = new IntegerArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, integerArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                integerArgumentDefinition.isValued());
        assertEquals(1, integerArgumentDefinition.getAlternativeNames().size());
    }

    @Test
    public void testIntegerArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<Integer> integerArgumentDefinition = new IntegerArgumentDefinition(testName, testDescription).addDefaultValue(defaultIntegerValue).makeMandatory();
        assertTrue(".hasDefaultValue() should be true",
                integerArgumentDefinition.hasDefaultValue());
        assertEquals(defaultIntegerValue, integerArgumentDefinition.getDefaultValue());
        assertTrue(".isMandatory() should be true",
                integerArgumentDefinition.isMandatory());
    }

    @Test
    public void testDoubleArgumentDefinitionConstructor() throws Exception {
        DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, doubleArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                doubleArgumentDefinition.isValued());
    }

    @Test
    public void testDoubleArgumentDefinitionConstructor2() throws Exception {
        DoubleArgumentDefinition doubleArgumentDefinition = new DoubleArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, doubleArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                doubleArgumentDefinition.isValued());
        assertEquals(1, doubleArgumentDefinition.getAlternativeNames().size());
    }

    @Test
    public void testDoubleArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<Double> doubleArgumentDefinition = new DoubleArgumentDefinition(testName, testDescription).addDefaultValue(defaultDoubleValue).makeMandatory();
        assertTrue(".hasDefaultValue() should be true",
                doubleArgumentDefinition.hasDefaultValue());
        assertEquals(defaultDoubleValue, doubleArgumentDefinition.getDefaultValue());
        assertTrue(".isMandatory() should be true",
                doubleArgumentDefinition.isMandatory());
    }

    @Test
    public void testBooleanArgumentDefinitionConstructor() throws Exception {
        BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, booleanArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                booleanArgumentDefinition.isValued());
    }

    @Test
    public void testBooleanArgumentDefinitionConstructor2() throws Exception {
        BooleanArgumentDefinition booleanArgumentDefinition = new BooleanArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(ArgumentDefinitionType.VALUED, booleanArgumentDefinition.getType());
        assertTrue(".isValued() should be true",
                booleanArgumentDefinition.isValued());
        assertEquals(1, booleanArgumentDefinition.getAlternativeNames().size());
    }

    @Test
    public void testBooleanArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<Boolean> booleanIArgumentDefinition = new BooleanArgumentDefinition(testName, testDescription).addDefaultValue(Boolean.TRUE).makeMandatory();
        assertTrue(".hasDefaultValue() should be true",
                booleanIArgumentDefinition.hasDefaultValue());
        assertEquals(Boolean.TRUE, booleanIArgumentDefinition.getDefaultValue());
        assertTrue(".isMandatory() should be true",
                booleanIArgumentDefinition.isMandatory());
    }

    @Test
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

    @Test
    public void testFlagArgumentDefinitionConstructor2() throws Exception {
        FlagArgumentDefinition flagArgumentDefinition = new FlagArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(ArgumentDefinitionType.FLAG, flagArgumentDefinition.getType());
        assertTrue(".isFlag() should be true",
                flagArgumentDefinition.isFlag());
        assertEquals(1, flagArgumentDefinition.getAlternativeNames().size());
    }

    @Test
    public void testFlagArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<Boolean> flagArgumentDefinition = new FlagArgumentDefinition(testName, testDescription).addDefaultValue(true).makeMandatory();
        assertTrue(".hasDefaultValue() should be true",
                flagArgumentDefinition.hasDefaultValue());
        assertEquals(Boolean.TRUE, flagArgumentDefinition.getDefaultValue());
        assertTrue(".isMandatory() should be true",
                flagArgumentDefinition.isMandatory());
    }

    @Test
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

    @Test
    public void testInformationalArgumentDefinitionConstructor2() throws Exception {
        InformationalArgumentDefinition informationalArgumentDefinition = new InformationalArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(ArgumentDefinitionType.INFORMATIONAL, informationalArgumentDefinition.getType());
        assertTrue(".isInformational() should be true",
                informationalArgumentDefinition.isInformational());
        assertEquals(1, informationalArgumentDefinition.getAlternativeNames().size());
    }

    @Test
    public void testInformationalArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<Boolean> informationalArgumentDefinition = new InformationalArgumentDefinition(testName, testDescription).addDefaultValue(true).makeMandatory();
        assertTrue("Default .hasDefaultValue() should be true",
                informationalArgumentDefinition.hasDefaultValue());
        assertNotNull(informationalArgumentDefinition.getDefaultValue());
        assertTrue(".isMandatory() should be true",
                informationalArgumentDefinition.isMandatory());
    }

    @Test
    public void testStringArgumentDefinitionMultiNamedConstructor() throws Exception {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[] {testName, testShortName}, testDescription);
        assertEquals(testName, stringArgumentDefinition.getName());
        assertEquals(1, stringArgumentDefinition.getAlternativeNames().size());
        assertTrue("Alternative names should contain alternative name",
                stringArgumentDefinition.getAlternativeNames().contains(testShortName));
    }

    @Test(expected = BadArgumentDefinitionException.class)
    public void testStringArgumentDefinitionFailsWithMultiSameNamedConstructor() {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[]{testName, testName}, testDescription);
    }

    @Test(expected = BadArgumentDefinitionException.class)
    public void testStringArgumentDefinitionFailsWithMultiSameNamed2Constructor() {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(new String[]{testName, testShortName, testShortName}, testDescription);
    }

    @Test
    public void testStringArgumentDefinitionChainedConstruction() throws Exception {
        ArgumentDefinition<String> stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription).addDefaultValue(defaultStringValue);
        assertTrue(".hasDefaultValue() should be true",
                stringArgumentDefinition.hasDefaultValue());
        assertEquals(defaultStringValue, stringArgumentDefinition.getDefaultValue());
    }

    @Test
    public void testStringArgumentDefinitionChained2Construction() throws Exception {
        ArgumentDefinition<String> stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription).addDefaultValue(defaultStringValue);
        assertTrue(".hasDefaultValue() should be true",
                stringArgumentDefinition.hasDefaultValue());
        assertEquals(defaultStringValue, stringArgumentDefinition.getDefaultValue());
    }

    @Test
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
        public Boolean convertRawValue(int tokenPosition, String rawValue, Argument<Boolean> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
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