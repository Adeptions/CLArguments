package com.adeptions.clarguments;

/**
 * Enumerator to define the categorized reasons for args parsing exceptions (BadArgumentException)
 */
public enum PredefinedBadArgumentReasons implements BadArgumentReason {
    MISSING_MANDATORY,
    INVALID_VALUE,
    MISSING_VALUE,
    INVALID_ARGUMENT_NAME_PREFIX,
    INVALID_ARGUMENT_NAME_SUFFIX,
    UNKNOWN_ARGUMENT,
    UNKNOWN_ARGUMENT_VALUE,
    UNEXPECTED_EXCEPTION,
    FLAG_WITH_VALUE,
    INFORMATIONAL_WITH_VALUE,
    MULTIPLE_ARGUMENT_NOT_ALLOWED,
    UNDEFINED,
}