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
package com.adeptions.arguments;

/**
 * Thrown to indicate that some problem was encountered during the definition of an argument or creating a collection
 * of argument definitions.
 *
 * Extends RuntimeException because an application cannot be reasonably expected to recover when the argument definitions
 * are erroneous.
 */
public class ArgumentDefinitionException extends RuntimeException {
	/**
	 * Constructs an ArgumentDefinitionException
	 */
	public ArgumentDefinitionException() {
		super();
	}

	/**
	 * Constructs an ArgumentDefinitionException with the specified message
	 * @param message the message description for the exception
	 */
	public ArgumentDefinitionException(String message) {
		super(message);
	}

	/**
	 * Constructs an ArgumentDefinitionException with the specified message and cause
	 * @param message the message description for the exception
	 * @param cause the cause of the exception
	 */
	public ArgumentDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an ArgumentDefinitionException with the specified cause
	 * @param cause the cause of the exception
	 */
	public ArgumentDefinitionException(Throwable cause) {
		super(cause);
	}
}