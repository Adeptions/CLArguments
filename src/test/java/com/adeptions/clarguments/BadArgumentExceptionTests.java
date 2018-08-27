package com.adeptions.clarguments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BadArgumentExceptionTests {
    private static final String testMessage = "This is a test exception message";

    @Test
    public void testBadArgExceptionConstructor() throws Exception {
        BadArgumentException badArgmentException = new BadArgumentException(PredefinedBadArgumentReasons.UNDEFINED, -1);
        assertNull(badArgmentException.getMessage());
        assertNull(badArgmentException.getCause());
        assertEquals(PredefinedBadArgumentReasons.UNDEFINED, badArgmentException.getReason());
    }

    @Test
    public void testBadArgExceptionConstructor2() throws Exception {
        BadArgumentException badArgmentException = new BadArgumentException(PredefinedBadArgumentReasons.UNDEFINED, -1, testMessage);
        assertEquals(testMessage, badArgmentException.getMessage());
        assertNull(badArgmentException.getCause());
        assertEquals(PredefinedBadArgumentReasons.UNDEFINED, badArgmentException.getReason());
    }

    @Test
    public void testBadArgExceptionConstructor3() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgumentException badArgmentException = new BadArgumentException(PredefinedBadArgumentReasons.UNDEFINED, -1, testMessage, cause);
        assertEquals(testMessage, badArgmentException.getMessage());
        assertEquals(PredefinedBadArgumentReasons.UNDEFINED, badArgmentException.getReason());
        assertEquals(cause, badArgmentException.getCause());
    }

    @Test
    public void testBadArgExceptionConstructor4() throws Exception {
        ArgumentName specifiedArgumentName = ArgumentName.parseFromSpacedArgToken(-1, ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgumentParsingOptions());
        BadArgumentException badArgmentException = new BadArgumentException(PredefinedBadArgumentReasons.UNDEFINED, -1, testMessage, specifiedArgumentName);
        assertEquals(testMessage, badArgmentException.getMessage());
        assertEquals(PredefinedBadArgumentReasons.UNDEFINED, badArgmentException.getReason());
        assertEquals(specifiedArgumentName, badArgmentException.getSpecifiedArgumentName());
    }

    @Test
    public void testBadArgExceptionConstructor5() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        ArgumentName specifiedArgumentName = ArgumentName.parseFromSpacedArgToken(-1, ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + "foo", new ArgumentParsingOptions());
        BadArgumentException badArgmentException = new BadArgumentException(PredefinedBadArgumentReasons.UNDEFINED, -1, testMessage, cause, specifiedArgumentName);
        assertEquals(testMessage, badArgmentException.getMessage());
        assertEquals(PredefinedBadArgumentReasons.UNDEFINED, badArgmentException.getReason());
        assertEquals(specifiedArgumentName, badArgmentException.getSpecifiedArgumentName());
        assertEquals(cause, badArgmentException.getCause());
    }
}