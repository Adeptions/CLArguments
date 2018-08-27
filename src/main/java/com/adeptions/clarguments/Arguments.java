package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.UNKNOWN_ARGUMENT;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.UNKNOWN_ARGUMENT_VALUE;

/**
 * Represents the arguments after parsing
 */
public final class Arguments {
    private ArgumentDefinitions argumentDefinitions;
    private BadArgExceptionsHandler badArgExceptionsHandler;
    private List<Argument> arguments = new ArrayList<>();
    private Map<String,Argument> argumentsMap = new HashMap<>();
    private List<BadArgumentException> exceptions = new ArrayList<>();
    private Map<String,ArgumentName> unknownArgNames = new HashMap<>();
    private List<String> unknownArgValues = new ArrayList<>();
    private boolean anythingSeen = false;

    /**
     * Constructs an Arguments with the specified arguments definitions and ars parsing exception handler
     * @param argumentDefinitions the list of argument definitions
     * @param badArgExceptionsHandler the args parsing exception handler to use
     */
    Arguments(ArgumentDefinitions argumentDefinitions, BadArgExceptionsHandler badArgExceptionsHandler) {
        this.argumentDefinitions = argumentDefinitions;
        if (badArgExceptionsHandler == null) {
            this.badArgExceptionsHandler = ArgumentParsingOptions.DEFAULT_BAD_ARG_EXCEPTION_HANDLER;
        } else {
            this.badArgExceptionsHandler = badArgExceptionsHandler;
        }
        initialize();
    }

    private void initialize() {
        for (ArgumentDefinition argumentDefinition: argumentDefinitions) {
            Argument argument = argumentDefinition.createArgumentInstance(this);
            arguments.add(argument);
            argumentsMap.put(argumentDefinition.getName(), argument);
            Iterator<String> alternativeNames = argumentDefinition.getAlternativeNames().iterator();
            while (alternativeNames.hasNext()) {
                argumentsMap.put(alternativeNames.next(), argument);
            }
        }
    }

    void foundValuedArg(int tokenPosition, Argument argument, ArgumentName specifiedArgumentName, String rawValue) throws BadArgumentException {
        anythingSeen = true;
        argument.setSeen();
        try {
            argument.setRawValue(tokenPosition, rawValue, specifiedArgumentName);
        } catch (BadArgumentException badArgumentException) {
            argument.addInvalidValue(rawValue);
            addParsingException(badArgumentException);
        }
    }

    void foundFlagOrInformationalArg(Argument argument, ArgumentName specifiedArgumentName) {
        anythingSeen = true;
        argument.setSeen();
        argument.setSpecified();
    }

    void foundUnknownArg(int tokenPosition, ArgumentName specifiedArgumentName) throws BadArgumentException {
        anythingSeen = true;
        unknownArgNames.put(specifiedArgumentName.getName(), specifiedArgumentName);
        addParsingException(new BadArgumentException(UNKNOWN_ARGUMENT, tokenPosition,
                "Unknown argument '" + specifiedArgumentName.getDisplayName() + "'", specifiedArgumentName));
    }

    void foundUnknownValue(int tokenPosition, String unknownValue, BadArgumentException cause) throws BadArgumentException {
        anythingSeen = true;
        unknownArgValues.add(unknownValue);
        addParsingException(new BadArgumentException(UNKNOWN_ARGUMENT_VALUE, tokenPosition,
                "Unknown argument value '" + unknownValue + "'", cause, new ArgumentName(unknownValue)));
    }

    /**
     * Gets an argument by the specified name
     * @param argumentName the argument name
     * @return the argument (should not be null assuming the argument name was in the original definitions)
     */
    public Argument get(String argumentName) {
        return argumentsMap.get(argumentName);
    }

    /**
     * Whether there were any information arguments specified
     * @return whether there were any information arguments specified
     */
    public boolean hasSpecifiedInformationals() {
        boolean result = false;
        for (Argument argument: arguments) {
            if (argument.isSpecified() && argument.getDefinition().isInformational()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of information arguments that were actually specified
     * @return the list of information arguments that were actually specified
     */
    public List<Argument> getSpecifiedInformationals() {
        List<Argument> result = new ArrayList<>(arguments);
        result.removeIf(argument -> !argument.isSpecified() || !argument.getDefinition().isInformational());
        return result;
    }

    /**
     * Whether there were any mandatory arguments that were not specified
     * @return whether there were any mandatory arguments that were not specified
     */
    public boolean hasMissingMandatories() {
        boolean result = false;
        for (Argument argument: arguments) {
            if (!argument.isSpecified() && argument.getDefinition().isMandatory()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of mandatory arguments that were not specified
     * @return the list of mandatory arguments that were not specified
     */
    public List<Argument> getMissingMandatories() {
        List<Argument> result = new ArrayList<>(arguments);
        result.removeIf(argument -> argument.isSpecified() || !argument.getDefinition().isMandatory());
        return result;
    }

    /**
     * Whether there were any unknown arg names found during parsing
     * @return whether there were any unknown arg names found during parsing
     */
    public boolean hasUnknownArgNames() {
        return unknownArgNames.size() != 0;
    }

    /**
     * Gets the list of unknown arg names found during parsing
     * @return the list of unknown arg names found during parsing
     */
    public Collection<ArgumentName> getUnknownArgNames() {
        return unknownArgNames.values();
    }

    /**
     * Whether there were any unknown arg values found during parsing
     * @return whether there were any unknown arg values found during parsing
     */
    public boolean hasUnknownArgValues() {
        return unknownArgValues.size() > 0;
    }

    /**
     * Gets the list of unknown arg values found during parsing
     * @return the list of unknown arg values found during parsing
     */
    public List<String> getUnknownArgValues() {
        return unknownArgValues;
    }

    /**
     * Gets the size of the arguments in the list (this is NOT the number of arguments found during parsing)
     * @return the size of the arguments in the list
     */
    public int size() {
        return arguments.size();
    }

    /**
     * Whether there were any args specified at parsing
     * @return whether there were any args specified at parsing
     */
    public boolean anySpecified() {
        boolean result = false;
        for (Argument argument: arguments) {
            if (argument.isSpecified()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of actually specified args
     * @return the list of actually specified args
     */
    public List<Argument> getSpecifiedArguments() {
        List<Argument> result = new ArrayList<>(arguments);
        result.removeIf((argument -> !argument.isSpecified()));
        return result;
    }

    /**
     * Whether there were any known args seen at parsing (even if they were invalid values)
     * @return whether there were any args specified at parsing
     */
    public boolean anySeenArguments() {
        boolean result = false;
        for (Argument argument: arguments) {
            if (argument.wasSeen()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of seen known args
     * @return the list of seen args
     */
    public List<Argument> getSeenArguments() {
        List<Argument> result = new ArrayList<>(arguments);
        result.removeIf((argument -> !argument.wasSeen()));
        return result;
    }

    /**
     * Whether there was anything seen during parsing (even if it was unknown or bad)
     * @return whether there was anything seen during parsing
     */
    public boolean anythingSeen() {
        return anythingSeen;
    }

    /**
     * Called by ArgumentDefinitions at start of parsing to denote whether anything was seen
     * (even if what was seen is unknown or invalid)
     * @param anythingSeen whether anything was seen in the args[]
     */
    void seenSomething(boolean anythingSeen) {
        this.anythingSeen = anythingSeen;
    }

    /**
     * Adds an exception encountered during parsing
     * @param badArgumentException the exception to be added
     * @throws BadArgumentException if the handler decides to throw the found exception
     */
    public void addParsingException(BadArgumentException badArgumentException) throws BadArgumentException {
        BadArgumentException storeException = null;
        try {
            storeException = badArgExceptionsHandler.handle(badArgumentException);
        } catch (BadArgumentException badArgumentException2) {
            storeException = badArgumentException2;
            throw badArgumentException2;
        } finally {
            if (storeException != null) {
                exceptions.add(storeException);
            }
        }
    }

    /**
     * Whether any parsing exceptions were found
     * @return whether any parsing exceptions were found
     */
    public boolean hasParsingExceptions() {
        return exceptions.size() != 0;
    }

    /**
     * Gets the list of parsing exceptions found
     * @return the list of parsing exceptions found
     */
    public List<BadArgumentException> getParsingExceptions() {
        return exceptions;
    }
}