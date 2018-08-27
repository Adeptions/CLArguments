package com.adeptions.clarguments;

/**
 * The options to be used when parsing args
 */
public class ArgumentParsingOptions {
    public static final String DEFAULT_ARG_NAME_PREFIX = "-";
    public static final Character DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE = ':';
    public static final BadArgumentExceptionsHandler DEFAULT_BAD_ARG_EXCEPTION_HANDLER = badArgumentException -> badArgumentException;

    private static final Character SPACE = ' ';

    protected boolean spaceBetweenArgumentNameAndValue;
    protected Character characterBetweenArgumentNameAndValue;
    protected String argumentNamePrefix;
    protected String argNameSuffix;
    protected BadArgumentExceptionsHandler badArgumentExceptionsHandler = DEFAULT_BAD_ARG_EXCEPTION_HANDLER;

    /**
     * Constructs an ArgumentParsingOptions with default options
     *
     * <p>Default options are:
     *   * spaced args
     *   * arg name prefix is "-" (@see com.adeptions.arguments.ArgumentParsingOptions.DEFAULT_ARG_NAME_PREFIX)
     * </p>
     */
    public ArgumentParsingOptions() {
        spaceBetweenArgumentNameAndValue = true;
        argumentNamePrefix = DEFAULT_ARG_NAME_PREFIX;
    }

    /**
     * Constructs an ArgumentParsingOptions with the specified arg name/value separator, arg name prefix and suffix
     * @param characterBetweenArgumentNameAndValue the character between the arg name and value
     *                                        (specify as null for spaced args)
     * @param argumentNamePrefix the expected prefix for arg names (or null if no prefix expected)
     * @param argNameSuffix the expected suffix for arg names (or null if no suffix expected)
     */
    public ArgumentParsingOptions(Character characterBetweenArgumentNameAndValue,
                                  String argumentNamePrefix,
                                  String argNameSuffix) {
        setCharacterBetweenArgumentNameAndValue(characterBetweenArgumentNameAndValue);
        setArgumentNamePrefix(argumentNamePrefix);
        setArgumentNameSuffix(argNameSuffix);
    }

    /**
     * Gets whether spaced style args are to be parsed
     * @return whether spaced style args are to be parsed
     */
    public boolean isSpaceBetweenArgumentNameAndValue() {
        return spaceBetweenArgumentNameAndValue;
    }

    /**
     * Sets whether spaced style args are to be parsed
     * @param spaceBetweenArgumentNameAndValue whether spaced style args are to be parsed
     *                                    (when false and no character between yet specified then the character between
     *                                     is defaulted to ':')
     */
    public void setSpaceBetweenArgumentNameAndValue(boolean spaceBetweenArgumentNameAndValue) {
        this.spaceBetweenArgumentNameAndValue = spaceBetweenArgumentNameAndValue;
        if (this.spaceBetweenArgumentNameAndValue) {
            characterBetweenArgumentNameAndValue = null;
        } else if (characterBetweenArgumentNameAndValue == null) {
            characterBetweenArgumentNameAndValue = DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE;
        }
    }

    /**
     * Gets the character expected between arg name and value
     * @return the character expected between arg name and value (null for spaced style args)
     */
    public Character getCharacterBetweenArgumentNameAndValue() {
        return characterBetweenArgumentNameAndValue;
    }

    /**
     * Sets the expected character between arg name and value
     * @param characterBetweenArgumentNameAndValue the character between arg name and value
     *                                        (if null specified then spaced args style is assumed)
     */
    public void setCharacterBetweenArgumentNameAndValue(Character characterBetweenArgumentNameAndValue) {
        this.characterBetweenArgumentNameAndValue = (SPACE.equals(characterBetweenArgumentNameAndValue) ? null : characterBetweenArgumentNameAndValue);
        spaceBetweenArgumentNameAndValue = (this.characterBetweenArgumentNameAndValue == null);
    }

    /**
     * Gets the expected arg name prefix
     * @return the expected arg name prefix
     */
    public String getArgumentNamePrefix() {
        return argumentNamePrefix;
    }

    /**
     * Sets the expected arg name prefix
     * @param argNamePrefix the expected arg name prefix (or null if no prefix expected)
     */
    public void setArgumentNamePrefix(String argNamePrefix) {
        this.argumentNamePrefix = argNamePrefix == null || argNamePrefix.isEmpty() ? null : argNamePrefix;
    }

    /**
     * Gets the expected arg name suffix
     * @return the expected arg name suffix
     */
    public String getArgumentNameSuffix() {
        return argNameSuffix;
    }

    /**
     * Sets the expected arg name suffix
     * @param argNameSuffix the expected arg name suffix (or null if no suffix expected)
     */
    public void setArgumentNameSuffix(String argNameSuffix) {
        this.argNameSuffix = argNameSuffix == null || argNameSuffix.isEmpty() ? null : argNameSuffix;
    }

    /**
     * Gets the args parsing exception handler
     * @return the args parsing exception handler
     */
    public BadArgumentExceptionsHandler getBadArgumentExceptionsHandler() {
        return badArgumentExceptionsHandler;
    }

    /**
     * Sets the bad arg exception handler
     * @param badArgumentExceptionsHandler the args parsing exception handler (if null is specified the default
     *                                    handler is used - which returns exceptions rather than throwing)
     */
    public void setBadArgsExceptionHandler(BadArgumentExceptionsHandler badArgumentExceptionsHandler) {
        if (badArgumentExceptionsHandler == null) {
            this.badArgumentExceptionsHandler = DEFAULT_BAD_ARG_EXCEPTION_HANDLER;
        } else {
            this.badArgumentExceptionsHandler = badArgumentExceptionsHandler;
        }
    }
}