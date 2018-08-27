package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.Argument;

/**
 * Thrown to indicate that some problem was encountered during args parsing
 */
public class BadArgumentException extends Exception {
    private BadArgumentReason reason;
    private Argument argument;
    private ArgumentName specifiedArgumentName;
    private int tokenPosition;

    /**
     * Constructs an BadArgumentException with the specified reason
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition) {
        super();
        this.reason = reason;
        this.tokenPosition = tokenPosition;
    }

    /**
     * Constructs an BadArgumentException with the specified reason and message
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message) {
        super(message);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message and argument
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param argument the argument on which the exception was encountered
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, Argument argument) {
        super(message);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
        this.argument = argument;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message, argument and specified arg name
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param argument the argument on which the exception was encountered
     * @param specifiedArgumentName the name by which the argument was specified
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, Argument argument, ArgumentName specifiedArgumentName) {
        super(message);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
        this.argument = argument;
        this.specifiedArgumentName = specifiedArgumentName;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message and specified arg name
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param specifiedArgumentName the name by which the argument was specified
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, ArgumentName specifiedArgumentName) {
        super(message);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
        this.specifiedArgumentName = specifiedArgumentName;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message and cause
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param cause the cause of the exception
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, Throwable cause) {
        super(message, cause);
        this.tokenPosition = tokenPosition;
        this.reason = reason;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message, cause, argument and specified arg name
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param cause the cause of the exception
     * @param argument the argument on which the exception was encountered
     * @param specifiedArgumentName the name by which the argument was specified
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, Throwable cause, Argument argument, ArgumentName specifiedArgumentName) {
        super(message, cause);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
        this.argument = argument;
        this.specifiedArgumentName = specifiedArgumentName;
    }

    /**
     * Constructs an BadArgumentException with the specified reason, message, cause and specified arg name
     * @param reason the reason for the exception
     * @param tokenPosition the position of the token (in original args[])
     * @param message the message description for the exception
     * @param cause the cause of the exception
     * @param specifiedArgumentName the name by which the argument was specified
     */
    public BadArgumentException(BadArgumentReason reason, int tokenPosition, String message, Throwable cause, ArgumentName specifiedArgumentName) {
        super(message, cause);
        this.reason = reason;
        this.tokenPosition = tokenPosition;
        this.specifiedArgumentName = specifiedArgumentName;
    }

    /**
     * Gets the reason for the exception
     * @return the reason for the exception
     */
    public BadArgumentReason getReason() {
        return reason;
    }

    /**
     * Gets the token position (in original args[]) for the exception
     * @return the token position (in original args[]) for the exception
     */
    public int getTokenPosition() {
        return tokenPosition;
    }

    /**
     * Gets the argument associated with the exception
     * @return the argument associated with the exception
     */
    public Argument getArgument() {
        return argument;
    }

    /**
     * Gets the specified name of the argument
     * @return the specified name of the argument
     */
    public ArgumentName getSpecifiedArgumentName() {
        return specifiedArgumentName;
    }
}