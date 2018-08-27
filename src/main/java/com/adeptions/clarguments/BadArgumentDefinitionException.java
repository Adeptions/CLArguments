package com.adeptions.clarguments;

/**
 * Thrown to indicate that some problem was encountered during the definition of an argument or creating a collection
 * of argument definitions.
 *
 * <p>Extends RuntimeException because an application cannot be reasonably expected to recover when the argument definitions
 * are erroneous.</p>
 */
public class BadArgumentDefinitionException extends RuntimeException {
    /**
     * Constructs an BadArgumentDefinitionException
     */
    public BadArgumentDefinitionException() {
        super();
    }

    /**
     * Constructs an BadArgumentDefinitionException with the specified message
     * @param message the message description for the exception
     */
    public BadArgumentDefinitionException(String message) {
        super(message);
    }

    /**
     * Constructs an BadArgumentDefinitionException with the specified message and cause
     * @param message the message description for the exception
     * @param cause the cause of the exception
     */
    public BadArgumentDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an BadArgumentDefinitionException with the specified cause
     * @param cause the cause of the exception
     */
    public BadArgumentDefinitionException(Throwable cause) {
        super(cause);
    }
}