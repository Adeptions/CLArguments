package com.adeptions.clarguments;

/**
 * Interface for handling BadArgumentException during parsing
 */
@FunctionalInterface
public interface BadArgumentExceptionsHandler {
    /**
     * Decides whether an encountered pasring exception should halt parsing immediately (by throwing the exception)
     * or should store the exception (by returning the exception) for reporting/handling after parsing completion
     *
     * @param badArgumentException the arg parsing exception to be handled
     * @return the exception to be stored (for reporting/handling after parsing completion) or null if
     *         the exception is swallowed
     * @throws BadArgumentException (if the exception should halt parsing immediately)
     */
    BadArgumentException handle(BadArgumentException badArgumentException) throws BadArgumentException;
}