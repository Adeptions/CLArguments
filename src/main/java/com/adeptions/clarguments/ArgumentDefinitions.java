package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.FLAG_WITH_VALUE;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INFORMATIONAL_WITH_VALUE;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MISSING_MANDATORY;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MISSING_VALUE;

/**
 * Represents a list of argument definitions and provides methods for parsing args
 */
public class ArgumentDefinitions implements Iterable<ArgumentDefinition> {
    protected List<ArgumentDefinition> definitions = new ArrayList<>();
    protected Map<String,ArgumentDefinition> definitionsNameMap = new HashMap<>();

    /**
     * Constructs an empty ArgumentDefinitions
     */
    public ArgumentDefinitions() {
    }

    /**
     * Constructs an ArgumentDefinitions with the specified argument definitions
     * @param argumentDefinitions the argument definitions
     * @throws BadArgumentDefinitionException if there was a problem with the argument definitions (e.g. a non-unique argument name)
     */
    public ArgumentDefinitions(ArgumentDefinition... argumentDefinitions) throws BadArgumentDefinitionException {
        addAll(Arrays.asList(argumentDefinitions));
    }

    /**
     * Constructs an ArgumentDefinitions with the specified argument definitions
     * @param argumentDefinitions the argument definitions
     * @throws BadArgumentDefinitionException if there was a problem with the argument definitions (e.g. a non-unique argument name)
     */
    public ArgumentDefinitions(List<ArgumentDefinition> argumentDefinitions) throws BadArgumentDefinitionException {
        addAll(argumentDefinitions);
    }

    /**
     * Gets whether the arguments contains an argument with the specified name
     * @param argumentName the argument name to be checked
     * @return whether the arguments contains an argument with the specified name
     */
    public boolean hasArgumentName(String argumentName) {
        return definitionsNameMap.containsKey(argumentName);
    }

    /**
     * Gets the argument definition for a specified argument name
     * @param argumentName the argument name
     * @return the argument definition (or null if no definition found for the specified argument name)
     */
    public ArgumentDefinition getArgumentDefinitionByName(String argumentName) {
        return definitionsNameMap.get(argumentName);
    }

    /**
     * Gets a constructed help string for the argument definitions using the specified args parsing options
     * @param argumentParsingOptions the args parsing options
     * @return the help string
     */
    public String getHelp(ArgumentParsingOptions argumentParsingOptions) {
        StringBuilder builder = new StringBuilder();
        Iterator<ArgumentDefinition> iterator = definitions.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next().getHelp(argumentParsingOptions))
                    .append(iterator.hasNext() ? "\n" : "");
        }
        return builder.toString();
    }

    /**
     * Gets a constructed help string for the argument definitions (using the default args parsing options)
     * @return the help string
     */
    public String getHelp() {
        return getHelp(new ArgumentParsingOptions());
    }

    /**
     * Main method for parsing args (using default ArgumentParsingOptions)
     * @param args the args to be parsed
     * @return the Arguments (from which values can be read)
     * @throws BadArgumentException if there were any parsing exception found (and thrown)
     */
    public Arguments parseArgs(String[] args) throws BadArgumentException {
        return parseArgs(args, null);
    }

    /**
     * Main method for parsing args using the specified ArgumentParsingOptions
     * @param args the args to be parsed
     * @param argumentParsingOptions the options for parsing the args
     * @return the Arguments (from which values can be read)
     * @throws BadArgumentException if there were any parsing exception found (and thrown)
     */
    public final Arguments parseArgs(String[] args, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        ArgumentParsingOptions useArgumentParsingOptions = (argumentParsingOptions == null ? new ArgumentParsingOptions() : argumentParsingOptions);
        Arguments result = new Arguments(this, useArgumentParsingOptions.getBadArgExceptionsHandler());
        result.seenSomething(args.length > 0);
        if (useArgumentParsingOptions.isSpaceBetweenArgumentNameAndValue()) {
            parseSpaceBetweenArgNameAndValue(args, result, useArgumentParsingOptions);
        } else {
            parseCharBetweenArgNameAndValue(args, result, useArgumentParsingOptions);
        }
        checkForMissingMandatoriesAfterParsing(result, useArgumentParsingOptions);
        return result;
    }

    protected void checkForMissingMandatoriesAfterParsing(Arguments arguments, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        if (!arguments.hasSpecifiedInformationals() && arguments.hasMissingMandatories()) {
            Collection<Argument> missingMandatories = arguments.getMissingMandatories();
            for (Argument argument: missingMandatories) {
                arguments.addParsingException(new BadArgumentException(MISSING_MANDATORY, -1,
                        "Missing mandatory argument: " + argument.getDefinition().getDisplayName(argumentParsingOptions), argument));
            }
        }
    }

    private static void parseSpaceBetweenArgNameAndValue(String[] args, Arguments arguments, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        ListIterator<String> iterator = Arrays.asList(args).listIterator();
        while (iterator.hasNext()) {
            int tokenPosition = iterator.previousIndex() + 1;
            String arg = iterator.next();
            ArgumentName argumentName = parseSpacedArgName(tokenPosition, arg, arguments, argumentParsingOptions);
            if (argumentName != null) {
                Argument argument = arguments.get(argumentName.getName());
                if (argument != null) {
                    if (argument.getDefinition().isValued()) {
                        if (!iterator.hasNext()) {
                            generateBadArgException(MISSING_VALUE, tokenPosition,
                                    "Missing value for argument '" + argumentName.getDisplayName() + "'", arguments, argumentParsingOptions, argument, argumentName);
                        } else {
                            String value = iterator.next();
                            if (checkValueIsNotArgName(value, arguments, argumentParsingOptions)) {
                                arguments.foundValuedArg(tokenPosition, argument, argumentName, value);
                            } else {
                                // the value turned out to be an arg name
                                // step backwards so that loop gets the next arg...
                                iterator.previous();
                                generateBadArgException(MISSING_VALUE, tokenPosition,
                                        "Missing value for argument '" + argumentName.getDisplayName() + "'", arguments, argumentParsingOptions, argument, argumentName);
                            }
                        }
                    } else {
                        arguments.foundFlagOrInformationalArg(argument, argumentName);
                    }
                } else {
                    arguments.foundUnknownArg(tokenPosition, argumentName);
                }
            }
        }
    }

    private static ArgumentName parseSpacedArgName(int tokenPosition, String arg, Arguments arguments, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        ArgumentName result = null;
        try {
            result = ArgumentName.parseFromSpacedArgToken(tokenPosition, arg, argumentParsingOptions);
        } catch (BadArgumentException badArgumentException) {
            arguments.foundUnknownValue(tokenPosition, arg, badArgumentException);
        }
        return result;
    }

    private static boolean checkValueIsNotArgName(String value, Arguments arguments, ArgumentParsingOptions argumentParsingOptions) {
        boolean result;
        // trap any exceptions that ArgumentName may throw...
        try {
            ArgumentName argumentName = ArgumentName.parseFromSpacedArgToken(-1, value, argumentParsingOptions);
            result = arguments.get(argumentName.getName()) == null;
        } catch (BadArgumentException badArgumentException) {
            // an exception means it's definitely not an arg name...
            result = true;
        }
        return result;
    }

    private static void parseCharBetweenArgNameAndValue(String[] args, Arguments arguments, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        for (int tokenPosition = 0; tokenPosition < args.length; tokenPosition++) {
            String arg = args[tokenPosition];
            ArgumentName argumentName = parseNonSpacedArg(tokenPosition, arg, arguments, argumentParsingOptions);
            if (argumentName != null) {
                Argument argument = arguments.get(argumentName.getName());
                if (argument != null) {
                    ArgumentDefinition definition = argument.getDefinition();
                    if (definition.isValued()) {
                        // even though the argument definition says it should have a value we allow for it not having a value
                        arguments.foundValuedArg(tokenPosition, argument, argumentName, argumentName.getValue());
                    } else if (argumentName.hasValue()) {
                        // non-valued (flag or informational) cannot have a value...
                        generateBadArgException((definition.isFlag() ? FLAG_WITH_VALUE : INFORMATIONAL_WITH_VALUE), tokenPosition,
                                definition.getType().getDescription() + " argument '" + argumentName.getDisplayName() + "' has unexpected value", arguments, argumentParsingOptions, argument, argumentName);
                    } else {
                        arguments.foundFlagOrInformationalArg(argument, argumentName);
                    }
                } else {
                    arguments.foundUnknownArg(tokenPosition, argumentName);
                }
            }
        }
    }

    private static ArgumentName parseNonSpacedArg(int tokenPosition, String arg, Arguments arguments, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        ArgumentName result = null;
        try {
            result = ArgumentName.parseFromNonSpacedArgToken(tokenPosition, arg, argumentParsingOptions);
        } catch (BadArgumentException badArgumentException) {
            arguments.foundUnknownValue(tokenPosition, arg, badArgumentException);
        }
        return result;
    }

    private static void generateBadArgException(BadArgumentReason reason, int tokenPosition, String message, Arguments arguments, ArgumentParsingOptions argumentParsingOptions, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        BadArgumentException badArgumentException = new BadArgumentException(reason, tokenPosition, message, argument, specifiedArgumentName);
        arguments.addParsingException(badArgumentException);
    }

    /**
     * Gets the size of the argument definitions in the list
     * @return the size of the argument definitions in the list
     */
    public int size() {
        return definitions.size();
    }

    /**
     * Gets whether the list of argument definitions is empty
     * @return whether the list of argument definitions is empty
     */
    public boolean isEmpty() {
        return definitions.isEmpty();
    }

    /**
     * Adds the specified argument definition to the list
     * @param argumentDefinition the argument definition to be added
     * @return true if this list contained the specified element
     */
    public boolean add(ArgumentDefinition argumentDefinition) {
        addNames(argumentDefinition);
        return definitions.add(argumentDefinition);
    }

    /**
     * Adds the specified argument definitions to the list
     * @param argumentDefinitions the argument definitions to be added
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(Collection<? extends ArgumentDefinition> argumentDefinitions) {
        for (ArgumentDefinition argumentDefinition: argumentDefinitions) {
            addNames(argumentDefinition);
        }
        return definitions.addAll(argumentDefinitions);
    }

    /**
     * Gets the indexed argument definition from the list
     * @param index the index of the argument definition
     * @return the argument definition
     */
    public ArgumentDefinition get(int index) {
        return definitions.get(index);
    }

    private void addNames(ArgumentDefinition argumentDefinition) {
        if (definitionsNameMap.containsKey(argumentDefinition.getName())) {
            throw new BadArgumentDefinitionException("Argument definitions already contains argument name '" + argumentDefinition.getName() + "'");
        }
        for (Object name: argumentDefinition.getAlternativeNames()) {
            if (definitionsNameMap.containsKey(name)) {
                throw new BadArgumentDefinitionException("Argument definitions already contains argument name '" + name + "'");
            }
        }
        addName(argumentDefinition.getName(), argumentDefinition);
        for (Object name: argumentDefinition.getAlternativeNames()) {
            addName((String)name, argumentDefinition);
        }
    }

    private void addName(String name, ArgumentDefinition argumentDefinition) {
        definitionsNameMap.put(name, argumentDefinition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<ArgumentDefinition> iterator() {
        return definitions.iterator();
    }
}