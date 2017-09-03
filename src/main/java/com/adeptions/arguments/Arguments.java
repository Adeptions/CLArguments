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

import java.util.*;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

/**
 * Represents the arguments after parsing
 */
public final class Arguments {
	private ArgumentDefinitions argumentDefinitions;
	private ArgsParsingExceptionHandler argsParsingExceptionHandler;
	private List<Argument> arguments = new ArrayList<Argument>();
	private Map<String,Argument> argumentsMap = new HashMap<String,Argument>();
	private Map<String,Argument> specifiedInformationals = new HashMap<String,Argument>();
	private List<ArgParsingException> exceptions = new ArrayList<ArgParsingException>();
	private Map<String,ArgName> unknownArgNames = new HashMap<String,ArgName>();
	private List<String> unknownArgValues = new ArrayList<String>();

	/**
	 * Constructs an Arguments with the specified arguments definitions and ars parsing exception handler
	 * @param argumentDefinitions the list of argument definitions
	 * @param argsParsingExceptionHandler the args parsing exception handler to use
	 */
	Arguments(ArgumentDefinitions argumentDefinitions, ArgsParsingExceptionHandler argsParsingExceptionHandler) {
		this.argumentDefinitions = argumentDefinitions;
		if (argsParsingExceptionHandler == null) {
			this.argsParsingExceptionHandler = ArgsParsingOptions.DEFAULT_ARGS_PARSING_EXCEPTION_HANDLER;
		} else {
			this.argsParsingExceptionHandler = argsParsingExceptionHandler;
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

	void foundValuedArg(int tokenPosition, Argument argument, ArgName specifiedArgName, String rawValue) throws ArgParsingException {
		ArgumentDefinition argumentDefinition = argument.getDefinition();
		String argumentName = argumentDefinition.getName();
		try {
			argument.setRawValue(tokenPosition, rawValue, specifiedArgName);
		} catch (ArgParsingException argsParsingException) {
			addParsingException(argsParsingException);
		}
	}

	void foundFlagOrInformationalArg(Argument argument, ArgName specifiedArgName) {
		ArgumentDefinition argumentDefinition = argument.getDefinition();
		String argumentName = argumentDefinition.getName();
		argument.setSpecified();
		if (argumentDefinition.isInformational()) {
			specifiedInformationals.put(argumentName, argument);
		}
	}

	void foundUnknownArg(int tokenPosition, ArgName specifiedArgName) throws ArgParsingException {
		unknownArgNames.put(specifiedArgName.getName(), specifiedArgName);
		addParsingException(new ArgParsingException(UNKNOWN_ARGUMENT, tokenPosition, "Unknown argument '" + specifiedArgName.getDisplayName() + "'", specifiedArgName));
	}

	void foundUnknownValue(int tokenPosition, String unknownValue, ArgParsingException cause) throws ArgParsingException {
		unknownArgValues.add(unknownValue);
		addParsingException(new ArgParsingException(UNKNOWN_ARGUMENT_VALUE, tokenPosition, "Unknown argument value '" + unknownValue + "'", cause, new ArgName(unknownValue)));
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
	 * Gets whether there were any information arguments specified
	 * @return whether there were any information arguments specified
	 */
	public boolean hasSpecifiedInformationals() {
		return specifiedInformationals.size() != 0;
	}

	/**
	 * Gets the list of information arguments that were actually specified
	 * @return the list of information arguments that were actually specified
	 */
	public Collection<Argument> getSpecifiedInformationals() {
		return specifiedInformationals.values();
	}

	/**
	 * Gets whether there were any mandatory arguments that were not specified
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
		List<Argument> result = new ArrayList<Argument>(arguments);
		result.removeIf(argument -> argument.isSpecified() || !argument.getDefinition().isMandatory());
		return result;
	}

	/**
	 * Gets whether there were any unknown arg names found during parsing
	 * @return whether there were any unknown arg names found during parsing
	 */
	public boolean hasUnknownArgNames() {
		return unknownArgNames.size() != 0;
	}

	/**
	 * Gets the list of unknown arg names found during parsing
	 * @return the list of unknown arg names found during parsing
	 */
	public Collection<ArgName> getUnknownArgNames() {
		return unknownArgNames.values();
	}

	/**
	 * Gets whether there were any unknown arg values found during parsing
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
	 * Gets whether there were any args specified at parsing
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
		List<Argument> result = new ArrayList<Argument>(arguments);
		result.removeIf((argument -> !argument.isSpecified()));
		return result;
	}

	/**
	 * Adds an exception encountered during parsing
	 * @param argsParsingException the exception to be added
	 * @throws ArgParsingException if the handler decides to throw the found exception
	 */
	public void addParsingException(ArgParsingException argsParsingException) throws ArgParsingException {
		ArgParsingException storeException = null;
		try {
			storeException = argsParsingExceptionHandler.handle(argsParsingException);
		} catch (ArgParsingException argsParsingException2) {
			storeException = argsParsingException2;
			throw argsParsingException2;
		} finally {
			if (storeException != null) {
				exceptions.add(storeException);
			}
		}
	}

	/**
	 * Gets whether any parsing exceptions were found
	 * @return whether any parsing exceptions were found
	 */
	public boolean hasParsingExceptions() {
		return exceptions.size() != 0;
	}

	/**
	 * Gets the list of parsing exceptions found
	 * @return the list of parsing exceptions found
	 */
	public List<ArgParsingException> getParsingExceptions() {
		return exceptions;
	}
}