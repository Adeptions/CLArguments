package com.adeptions;

import com.adeptions.clarguments.ArgumentDefinitions;
import com.adeptions.clarguments.ArgumentParsingOptions;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.converters.MultiValueBooleanConverter;
import com.adeptions.clarguments.definitions.BooleanArgumentDefinition;
import com.adeptions.clarguments.definitions.DoubleArgumentDefinition;
import com.adeptions.clarguments.definitions.FileArgumentDefinition;
import com.adeptions.clarguments.definitions.InformationalArgumentDefinition;
import com.adeptions.clarguments.definitions.IntegerArgumentDefinition;
import com.adeptions.clarguments.definitions.StringArgumentDefinition;
import com.adeptions.clarguments.validators.DisallowMultiplesValueValidator;
import com.adeptions.clarguments.validators.RangedDoubleValueValidator;
import com.adeptions.clarguments.validators.RangedIntegerValueValidator;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MISSING_VALUE;

public class Application {
    /**
     * Main startup
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                new BooleanArgumentDefinition("b", "A yes/no boolean").addValueConverter(new MultiValueBooleanConverter(true, false,
                        "yes|no", "y|n", "1|0", "on|off", "true|false")),
                new DoubleArgumentDefinition("n", "A number").addAdditionalValueValidator(new RangedDoubleValueValidator(1d, 10d)),
                new IntegerArgumentDefinition("i", "An integer")
                        .addAdditionalValueValidator(new DisallowMultiplesValueValidator())
                        .addAdditionalValueValidator(new RangedIntegerValueValidator(1, 10)),
                new FileArgumentDefinition("f", "A file"),
                new StringArgumentDefinition(new String[] {"say", "s"}, "What to say").makeMandatory()
                        .addValueValidator((tokenPosition, value, argument, specifiedArgName) -> {
                            if (value == null) {
                                throw new BadArgumentException(MISSING_VALUE, tokenPosition,
                                        "Argument '" + specifiedArgName.getDisplayName() + "' must have a value", argument);
                            }
                            if (value.contains(" ")) {
                                argument.setSpecified();
                                throw new BadArgumentException(INVALID_VALUE, tokenPosition, "Cannot contain spaces?", argument);
                            }
                            return value;
                        }
                ),
                new InformationalArgumentDefinition(new String[] {"version", "v"}, "Show version"),
                new InformationalArgumentDefinition(new String[] {"help", "h"}, "Show this help")
        );
        ArgumentParsingOptions argumentParsingOptions = new ArgumentParsingOptions(':', "-", null);
        argumentParsingOptions.setBadArgsExceptionHandler(badArgumentException -> badArgumentException);
        try {
            Arguments arguments = argumentDefinitions.parseArgs(args, argumentParsingOptions);
            printVersionIfRequested(arguments);
            if (arguments.hasParsingExceptions()) {
                for (BadArgumentException badArgumentException: arguments.getParsingExceptions()) {
                    System.err.println(badArgumentException.getMessage() + " (at position " + badArgumentException.getTokenPosition() + ")");
                }
            } else if (!arguments.anySpecified() || arguments.get("help").isSpecified()) {
                System.out.println("Help:-");
                System.out.println(argumentDefinitions.getHelp(argumentParsingOptions));
            } else if (!arguments.hasSpecifiedInformationals()) {
                System.out.println("Say... " + arguments.get("say").getValue());
            }
        } catch (BadArgumentException e) {
            e.printStackTrace();
        }
    }

    private static void printVersionIfRequested(Arguments arguments) {
        if (arguments.get("version").isSpecified()) {
            System.out.println("Version: 1.0-SNAPSHOT");
        }
    }
}
