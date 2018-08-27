package com.adeptions.clarguments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentNameTests {

    @Test
    public void testArgNameSpaced() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "-foo", argumentParsingOptions);
        assertEquals("foo", argumentName.getName());
        assertEquals("-foo", argumentName.getDisplayName());
        assertEquals("-foo", argumentName.getRawToken());
    }

    @Test
    public void testArgNameSpaced2() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(null, "-", "+");
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "-foo+", argumentParsingOptions);
        assertEquals("foo", argumentName.getName());
        assertEquals("-foo+", argumentName.getDisplayName());
        assertEquals("-foo+", argumentName.getRawToken());
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced3() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "foo", argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced4() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(null, "-", "+");
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "foo", argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced5() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(null, "-", "+");
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "-foo", argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced6() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, "foo", argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced7() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        argumentParsingOptions.setBadArgsExceptionHandler(badArgumentException -> {
            throw badArgumentException;
        });
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, null, argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgNameSpaced8() throws Exception {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, null, argumentParsingOptions);
    }
}