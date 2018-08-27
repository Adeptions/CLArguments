package com.adeptions.clarguments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentDefinitionExceptionTests {
    private static final String testMessage = "This is a test exception message";

    @Test
    public void testBadArgumentDefinitionExceptionConstructor2() throws Exception {
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(testMessage);
        assertEquals(testMessage, badArgumentDefinitionException.getMessage());
    }

    @Test
    public void testBadArgumentDefinitionExceptionConstructor3() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(testMessage, cause);
        assertEquals(testMessage, badArgumentDefinitionException.getMessage());
        assertEquals(cause, badArgumentDefinitionException.getCause());
    }

    @Test
    public void testBadArgumentDefinitionExceptionConstructor4() throws Exception {
        IllegalArgumentException cause = new IllegalArgumentException();
        BadArgumentDefinitionException badArgumentDefinitionException = new BadArgumentDefinitionException(cause);
        assertEquals(cause, badArgumentDefinitionException.getCause());
    }
}