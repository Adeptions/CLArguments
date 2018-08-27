package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.definitions.ArgumentDefinition;
import com.adeptions.clarguments.definitions.BooleanArgumentDefinition;
import com.adeptions.clarguments.definitions.DoubleArgumentDefinition;
import com.adeptions.clarguments.definitions.FlagArgumentDefinition;
import com.adeptions.clarguments.definitions.InformationalArgumentDefinition;
import com.adeptions.clarguments.definitions.IntegerArgumentDefinition;
import com.adeptions.clarguments.definitions.StringArgumentDefinition;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ArgsParsing_SpaceBetweenTests {
    private static final String testArgumentName1 = "string1";
    private static final String testAlternateArgumentName1 = "s1";
    private static final String testArgumentName2 = "integer2";
    private static final String testAlternateArgumentName2 = "i2";
    private static final String testArgumentName3 = "double3";
    private static final String testArgumentName4 = "flag4";
    private static final String testArgumentName5 = "help";
    private static final String testAlternateArgumentName5 = "h";
    private static final String testArgumentName6 = "boolean6";

    private static final String[] allArgumentNames = new String[] {
            testArgumentName1, testAlternateArgumentName1,
            testArgumentName2, testAlternateArgumentName2,
            testArgumentName3,
            testArgumentName4,
            testArgumentName5
    };
    private static final String testDescription = "This is an argument definition description";

    private static final ArgumentDefinition argumentDefinition1 = new StringArgumentDefinition(new String[] {testArgumentName1, testAlternateArgumentName1}, testDescription).makeMandatory();
    private static final ArgumentDefinition argumentDefinition2 = new IntegerArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName2}, testDescription);
    private static final ArgumentDefinition argumentDefinition3 = new DoubleArgumentDefinition(testArgumentName3, testDescription);
    private static final ArgumentDefinition argumentDefinition4 = new FlagArgumentDefinition(testArgumentName4, testDescription);
    private static final ArgumentDefinition argumentDefinition5 = new InformationalArgumentDefinition(new String[] {testArgumentName5, testAlternateArgumentName5}, testDescription);
    private static final ArgumentDefinition argumentDefinition6 = new BooleanArgumentDefinition(testArgumentName6, testDescription);

    @Test
    public void testArgsParsingEmptyArgs() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition1,
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5,
                argumentDefinition6
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[0]);
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertFalse("No arguments were specified", arguments.anySpecified());
        assertTrue(".hasParsingExceptions() should be true (there was one missing mandatory argument)",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
        for (String argumentName: allArgumentNames) {
            assertNotNull(arguments.get(argumentName));
            assertFalse("Argument .isSpecified() should be false",
                    arguments.get(argumentName).isSpecified());
        }
        assertFalse(".hasSpecifiedInformationals() should be false",
                arguments.hasSpecifiedInformationals());
        assertEquals(0, arguments.getSpecifiedInformationals().size());
        assertTrue(".hasMissingMandatories() should be true",
                arguments.hasMissingMandatories());
        assertEquals(1, arguments.getMissingMandatories().size());
    }

    @Test
    public void testArgsParsingManyArgs() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition1,
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5,
                argumentDefinition6
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1, "foo",
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2, "123",
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3, "123.4",
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4,
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testAlternateArgumentName5,
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName6, "true"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(argumentDefinitions.size(), arguments.getSpecifiedArguments().size());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName1).isSpecified());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName2).isSpecified());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName3).isSpecified());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName4).isSpecified());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName5).isSpecified());
        assertTrue(".isSpecified() should be true",
                arguments.get(testArgumentName6).isSpecified());
        assertTrue(".hasSpecifiedInformationals() should be true",
                arguments.hasSpecifiedInformationals());
        assertEquals("foo", (String)arguments.get(testArgumentName1).getValue());
        assertEquals((Integer)123, (Integer)arguments.get(testArgumentName2).getValue());
        assertEquals((Double)123.4, (Double)arguments.get(testArgumentName3).getValue());
        assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName4).getValue());
        assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName5).getValue());
        assertEquals(Boolean.TRUE, (Boolean)arguments.get(testArgumentName6).getValue());
    }

    @Test
    public void testArgsParsingOneMandatoryStringArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition1,
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1,
                "foo"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument<String> argument = arguments.get(testArgumentName1);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
        assertEquals("foo", argument.getValue());
    }

    @Test
    public void testArgsParsingOneMandatoryStringMissingValueArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition1,
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        Argument<String> argument = arguments.get(testArgumentName1);
        assertNotNull(argument);
        assertFalse("Argument .isSpecified() should be false",
                argument.isSpecified());
        assertTrue("There should be parsing exceptions",
                arguments.hasParsingExceptions());
        assertEquals(2, arguments.getParsingExceptions().size());
        assertTrue(".hasMissingMandatories() should be true",
                arguments.hasMissingMandatories());
        assertEquals(1, arguments.getMissingMandatories().size());
    }

    @Test(expected = BadArgumentException.class)
    public void testArgsParsingMissingMandatoryArgFailsImmediatelt() throws BadArgumentException {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition1,
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        argumentParsingOptions.setBadArgsExceptionHandler(badArgmentException -> {
            throw badArgmentException;
        });
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {}, argumentParsingOptions);
    }

    @Test
    public void testArgsParsingOneIntegerArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
                "123"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName2);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
        Integer argValue = (Integer)argument.getValue();
        assertEquals((Integer)123, argValue);
    }

    @Test
    public void testArgsParsingOneDoublerArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition3
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
                "123.4"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName3);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
        Double argValue = (Double)argument.getValue();
        assertEquals((Double)123.4, argValue);
    }

    @Test
    public void testArgsParsingOneFlagArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition4
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName4);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
        Boolean argValue = (Boolean)argument.getValue();
        assertEquals(Boolean.TRUE, argValue);
    }

    @Test
    public void testArgsParsingOneInformationalArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName5
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName5);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
        Boolean argValue = (Boolean)argument.getValue();
        assertEquals(Boolean.TRUE, argValue);
        assertTrue(".hasSpecifiedInformationals() should be true",
                arguments.hasSpecifiedInformationals());
        assertEquals(1, arguments.getSpecifiedInformationals().size());
    }

    @Test
    public void testArgsParsingOneBadIntegerArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
                "xxx"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName2);
        assertNotNull(argument);
        assertFalse("Argument .isSpecified() should be false",
                argument.isSpecified());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
    }

    @Test(expected = BadArgumentException.class)
    public void testArgsParsingOneBadIntegerArgFailsImmediately() throws BadArgumentException {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2
        );
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        argumentParsingOptions.setBadArgsExceptionHandler(badArgmentException -> {
            throw badArgmentException;
        });
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName2,
                "xxx"
            }, argumentParsingOptions);
    }

    @Test
    public void testArgsParsingMissingMandatoryFlagArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                new FlagArgumentDefinition(testArgumentName4, testDescription).makeMandatory()
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4,
        });
        assertFalse(".hasMissingMandatories() should be false",
                arguments.hasMissingMandatories());
    }

    @Test
    public void testArgsParsingOneBadDoublerArg() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition3
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
                "xxx"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName3);
        assertNotNull(argument);
        assertFalse("Argument .isSpecified() should be false",
                argument.isSpecified());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
    }

    @Test
    public void testArgsParsingCheckMissingValueFollowedByArgName() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition3,
                argumentDefinition4
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName3,
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName4
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(1, arguments.getSpecifiedArguments().size());
        Argument argument = arguments.get(testArgumentName3);
        assertNotNull(argument);
        assertFalse("Argument .isSpecified() should be false",
                argument.isSpecified());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
        argument = arguments.get(testArgumentName4);
        assertNotNull(argument);
        assertTrue("Argument .isSpecified() should be true",
                argument.isSpecified());
    }

    @Test
    public void testArgsParsingUnknownArgName() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + "badArgName"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
        assertTrue(".hasUnknownArgs() should be true",
                arguments.hasUnknownArgNames());
        assertEquals(1, arguments.getUnknownArgNames().size());
    }

    @Test
    public void testArgsParsingUnknownArgValue() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                "badArgName"
        });
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
        assertTrue(".hasUnknownArgValues() should be true",
                arguments.hasUnknownArgValues());
        assertEquals(1, arguments.getUnknownArgValues().size());
    }

    @Test
    public void testArgsParsingUnknownArgValue2() throws Exception {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                "badArgName"
        }, new ArgumentParsingOptions(null, null, null));
        assertEquals(argumentDefinitions.size(), arguments.size());
        assertEquals(0, arguments.getSpecifiedArguments().size());
        assertTrue(".hasParsingExceptions() should be true",
                arguments.hasParsingExceptions());
        assertEquals(1, arguments.getParsingExceptions().size());
        assertTrue(".hasUnknownArgs() should be true",
                arguments.hasUnknownArgNames());
        assertEquals(1, arguments.getUnknownArgNames().size());
    }

    @Test(expected = BadArgumentException.class)
    public void testArgsParsingUnknownArgValue3() throws BadArgumentException {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        argumentParsingOptions.setBadArgsExceptionHandler(badArgmentException -> {
            throw badArgmentException;
        });
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX + "badArgName"
                }, argumentParsingOptions);
    }

    @Test(expected = BadArgumentException.class)
    public void testArgsParsingUnknownArgValue4() throws BadArgumentException {
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions();
        argumentParsingOptions.setBadArgsExceptionHandler(badArgmentException -> {
            throw badArgmentException;
        });
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                argumentDefinition2,
                argumentDefinition3,
                argumentDefinition4,
                argumentDefinition5
        );
        Arguments arguments = argumentDefinitions.parseArgs(new String[] {
                    "badArgName"
            }, argumentParsingOptions);
    }
}