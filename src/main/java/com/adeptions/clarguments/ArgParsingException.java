/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.clarguments;

import com.adeptions.clarguments.arguments.*;

/**
 * Thrown to indicate that some problem was encountered during args parsing
 */
public class ArgParsingException extends Exception {
	private ArgParsingExceptionReason reason;
	private Argument argument;
	private ArgName specifiedArgName;
	private int tokenPosition;

	/**
	 * Constructs an ArgParsingException with the specified reason
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition) {
		super();
		this.reason = reason;
		this.tokenPosition = tokenPosition;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason and message
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message) {
		super(message);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message and argument
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param argument the argument on which the exception was encountered
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Argument argument) {
		super(message);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
		this.argument = argument;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message, argument and specified arg name
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param argument the argument on which the exception was encountered
	 * @param specifiedArgName the name by which the argument was specified
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Argument argument, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message and specified arg name
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param specifiedArgName the name by which the argument was specified
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, ArgName specifiedArgName) {
		super(message);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
		this.specifiedArgName = specifiedArgName;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message and cause
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param cause the cause of the exception
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Throwable cause) {
		super(message, cause);
		this.tokenPosition = tokenPosition;
		this.reason = reason;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message, cause, argument and specified arg name
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param cause the cause of the exception
	 * @param argument the argument on which the exception was encountered
	 * @param specifiedArgName the name by which the argument was specified
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Throwable cause, Argument argument, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
		this.argument = argument;
		this.specifiedArgName = specifiedArgName;
	}

	/**
	 * Constructs an ArgParsingException with the specified reason, message, cause and specified arg name
	 * @param reason the reason for the exception
	 * @param tokenPosition the position of the token (in original args[])
	 * @param message the message description for the exception
	 * @param cause the cause of the exception
	 * @param specifiedArgName the name by which the argument was specified
	 */
	public ArgParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Throwable cause, ArgName specifiedArgName) {
		super(message, cause);
		this.reason = reason;
		this.tokenPosition = tokenPosition;
		this.specifiedArgName = specifiedArgName;
	}

	/**
	 * Gets the reason for the exception
	 * @return the reason for the exception
	 */
	public ArgParsingExceptionReason getReason() {
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
	public ArgName getSpecifiedArgName() {
		return specifiedArgName;
	}
}