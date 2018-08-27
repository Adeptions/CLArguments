package com.adeptions.clarguments;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_ARGUMENT_NAME_PREFIX;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_ARGUMENT_NAME_SUFFIX;
import static com.adeptions.clarguments.PredefinedBadArgumentReasons.UNEXPECTED_EXCEPTION;

/**
 * Represents an arg name found during parsing
 */
public class ArgumentName {
    private String rawToken;
    private String name;
    private String displayName;
    private String value;

    /**
     * Constructs an ArgumentName with the specified raw arg token found
     * @param rawToken the raw arg token found
     */
    ArgumentName(String rawToken) {
        this.rawToken = rawToken;
    }

    /**
     * Gets the raw token found
     * @return the raw token found
     */
    public String getRawToken() {
        return rawToken;
    }

    /**
     * Gets the parsed name of the arg found
     * @return the parsed name of the arg found (may be null if name was invalid)
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the parsed display name of the arg found
     * (i.e. with any prefix/suffix defined by the parsing options added)
     * @return the parsed display name of the arg found (may be null if name was invalid)
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets whether the arg token also incorporated a value
     * (NB. Only relevant when pasring args in the format name=value)
     * @return whether the arg token also incorporated a value
     */
    public boolean hasValue() {
        return value != null;
    }

    /**
     * Gets the value of the arg
     * (NB. Only relevant when pasring args in the format name=value)
     * @return the value of the arg
     */
    public String getValue() {
        return value;
    }

    /**
     * Parses a found arg token into an ArgumentName (used when parsing spaced args)
     * @param tokenPosition the position of the token (in original args[])
     * @param rawToken the raw args token encountered
     * @param argumentParsingOptions the parsing options
     * @return the created ArgumentName
     * @throws BadArgumentException if there was a problem with the token (e.g. the name was invalid)
     */
    static ArgumentName parseFromSpacedArgToken(int tokenPosition, String rawToken, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        String argNamePrefix = argumentParsingOptions.getArgumentNamePrefix();
        String argNameSuffix = argumentParsingOptions.getArgumentNameSuffix();
        ArgumentName result = new ArgumentName(rawToken);
        String name = rawToken;
        try {
            if (argNamePrefix != null) {
                if (name.startsWith(argNamePrefix)) {
                    name = name.substring(argNamePrefix.length());
                } else {
                    throw new BadArgumentException(INVALID_ARGUMENT_NAME_PREFIX, tokenPosition,
                            "Argument names must begin with '" + argNamePrefix + "'", result);
                }
            }
            if (argNameSuffix != null) {
                if (name.endsWith(argNameSuffix)) {
                    name = name.substring(0, name.length() - argNameSuffix.length());
                } else {
                    throw new BadArgumentException(INVALID_ARGUMENT_NAME_SUFFIX, tokenPosition, "Argument names must end with '" + argNameSuffix + "'", result);
                }
            }
            result.name = name;
            result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
                    name +
                    (argNameSuffix != null ? argNameSuffix : "");
        } catch (BadArgumentException badArgumentException) {
            throw badArgumentException;
        } catch (Exception ex) {
            throw new BadArgumentException(UNEXPECTED_EXCEPTION, tokenPosition, "Unknown error parsing argument name", ex, result);
        }
        return result;
    }

    /**
     * Parses a found arg token into an ArgumentName (used when parsing non-spaced args)
     * @param tokenPosition the position of the token (in original args[])
     * @param rawToken the raw args token encountered
     * @param argumentParsingOptions the parsing options
     * @return the created ArgumentName
     * @throws BadArgumentException if there was a problem with the token (e.g. the name was invalid)
     */
    static ArgumentName parseFromNonSpacedArgToken(int tokenPosition, String rawToken, ArgumentParsingOptions argumentParsingOptions) throws BadArgumentException {
        String argNamePrefix = argumentParsingOptions.getArgumentNamePrefix();
        String argNameSuffix = argumentParsingOptions.getArgumentNameSuffix();
        Character argNameAndValueSeparator = argumentParsingOptions.getCharacterBetweenArgumentNameAndValue();
        ArgumentName result = new ArgumentName(rawToken);
        String name = rawToken;
        int separatorAt;
        try {
            if (argNamePrefix != null) {
                if (name.startsWith(argNamePrefix)) {
                    name = name.substring(argNamePrefix.length());
                } else {
                    throw new BadArgumentException(INVALID_ARGUMENT_NAME_PREFIX, tokenPosition,
                            "Argument names must begin with '" + argNamePrefix + "'", result);
                }
            }
            if ((separatorAt = name.indexOf(argNameAndValueSeparator)) != -1) {
                result.value = name.substring(separatorAt + 1);
                name = name.substring(0, separatorAt);
            }
            if (argNameSuffix != null) {
                if (name.endsWith(argNameSuffix)) {
                    name = name.substring(0, name.length() - argNameSuffix.length());
                } else {
                    throw new BadArgumentException(INVALID_ARGUMENT_NAME_SUFFIX, tokenPosition,
                            "Argument names must end with '" + argNameSuffix + "'", result);
                }
            }
            result.name = name;
            result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
                    name +
                    (argNameSuffix != null ? argNameSuffix : "");
        } catch (BadArgumentException badArgumentException) {
            throw badArgumentException;
        } catch (Exception ex) {
            throw new BadArgumentException(UNEXPECTED_EXCEPTION, tokenPosition, "Unknown error parsing argument name", ex, result);
        }
        return result;
    }
}