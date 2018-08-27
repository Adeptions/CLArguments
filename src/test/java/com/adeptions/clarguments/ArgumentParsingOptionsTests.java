package com.adeptions.clarguments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ArgumentParsingOptionsTests {
    private static final Character alternateCharBetweenArgNameAndValue = '=';
    private static final String alternateArgNamePrefix = "[";
    private static final String alternateArgNameSuffix = "]";

    @Test
    public void testArgsParsingOptionsConstructor() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        assertTrue("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertEquals(ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX, argumentParsingOptions.getArgumentNamePrefix());
        assertNull(argumentParsingOptions.getArgumentNameSuffix());
        assertNull(argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsConstructor2() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
        assertEquals(alternateCharBetweenArgNameAndValue, argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
        assertFalse("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getArgumentNamePrefix());
        assertNull(argumentParsingOptions.getArgumentNameSuffix());
    }

    @Test
    public void testArgsParsingOptionsConstructor3() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(null, null, null);
        assertTrue("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getArgumentNamePrefix());
        assertNull(argumentParsingOptions.getArgumentNameSuffix());
    }

    @Test
    public void testArgsParsingOptionsConstructor4() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(null, alternateArgNamePrefix, alternateArgNameSuffix);
        assertTrue("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
        assertEquals(alternateArgNamePrefix, argumentParsingOptions.getArgumentNamePrefix());
        assertEquals(alternateArgNameSuffix, argumentParsingOptions.getArgumentNameSuffix());
    }

    @Test
    public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        assertTrue("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        argumentParsingOptions.setCharacterBetweenArgumentNameAndValue(alternateCharBetweenArgNameAndValue);
        assertFalse(".isSpaceBetweenArgumentNameAndValue() should now be false",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue2() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
        assertFalse(".isSpaceBetweenArgumentNameAndValue() should be false",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        argumentParsingOptions.setCharacterBetweenArgumentNameAndValue(null);
        assertTrue(".isSpaceBetweenArgumentNameAndValue() should now be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsSetCharacterBetweenArgNameAndValue3() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
        assertFalse(".isSpaceBetweenArgumentNameAndValue() should be false",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        argumentParsingOptions.setCharacterBetweenArgumentNameAndValue(' ');
        assertTrue(".isSpaceBetweenArgumentNameAndValue() should now be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsSetSpaceBtweenArgNameAndValue() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(alternateCharBetweenArgNameAndValue, null, null);
        assertFalse(".isSpaceBetweenArgumentNameAndValue() should be false",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        argumentParsingOptions.setSpaceBetweenArgumentNameAndValue(true);
        assertTrue(".isSpaceBetweenArgumentNameAndValue() should now be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertNull(argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsSetSpaceBtweenArgNameAndValue2() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        assertTrue("Default .isSpaceBetweenArgumentNameAndValue() should be true",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        argumentParsingOptions.setSpaceBetweenArgumentNameAndValue(false);
        assertFalse(".isSpaceBetweenArgumentNameAndValue() should now be false",
                argumentParsingOptions.isSpaceBetweenArgumentNameAndValue());
        assertEquals(ArgumentParsingOptions.DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE, argumentParsingOptions.getCharacterBetweenArgumentNameAndValue());
    }

    @Test
    public void testArgsParsingOptionsSetArgNamePrefix() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        assertEquals(ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX, argumentParsingOptions.getArgumentNamePrefix());
        argumentParsingOptions.setArgumentNamePrefix(alternateArgNamePrefix);
        assertEquals(alternateArgNamePrefix, argumentParsingOptions.getArgumentNamePrefix());
    }

    @Test
    public void testArgsParsingOptionsSetArgNameSuffix() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        assertNull(argumentParsingOptions.getArgumentNameSuffix());
        argumentParsingOptions.setArgumentNameSuffix(alternateArgNameSuffix);
        assertEquals(alternateArgNameSuffix, argumentParsingOptions.getArgumentNameSuffix());
    }
}