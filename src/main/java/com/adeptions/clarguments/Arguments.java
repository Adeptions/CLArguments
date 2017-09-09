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
import com.adeptions.clarguments.definitions.*;

import java.util.*;

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

/**
 * Represents the arguments after parsing
 */
public final class Arguments {
	private ArgumentDefinitions argumentDefinitions;
	private BadArgExceptionsHandler badArgExceptionsHandler;
	private List<Argument> arguments = new ArrayList<Argument>();
	private Map<String,Argument> argumentsMap = new HashMap<String,Argument>();
	private List<BadArgException> exceptions = new ArrayList<BadArgException>();
	private Map<String,ArgName> unknownArgNames = new HashMap<String,ArgName>();
	private List<String> unknownArgValues = new ArrayList<String>();
	private boolean anythingSeen = false;

	/**
	 * Constructs an Arguments with the specified arguments definitions and ars parsing exception handler
	 * @param argumentDefinitions the list of argument definitions
	 * @param badArgExceptionsHandler the args parsing exception handler to use
	 */
	Arguments(ArgumentDefinitions argumentDefinitions, BadArgExceptionsHandler badArgExceptionsHandler) {
		this.argumentDefinitions = argumentDefinitions;
		if (badArgExceptionsHandler == null) {
			this.badArgExceptionsHandler = ArgsParsingOptions.DEFAULT_BAD_ARG_EXCEPTION_HANDLER;
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

	void foundValuedArg(int tokenPosition, Argument argument, ArgName specifiedArgName, String rawValue) throws BadArgException {
		anythingSeen = true;
		argument.setSeen();
		try {
			argument.setRawValue(tokenPosition, rawValue, specifiedArgName);
		} catch (BadArgException badArgException) {
			argument.addInvalidValue(rawValue);
			addParsingException(badArgException);
		}
	}

	void foundFlagOrInformationalArg(Argument argument, ArgName specifiedArgName) {
		anythingSeen = true;
		argument.setSeen();
		argument.setSpecified();
	}

	void foundUnknownArg(int tokenPosition, ArgName specifiedArgName) throws BadArgException {
		anythingSeen = true;
		unknownArgNames.put(specifiedArgName.getName(), specifiedArgName);
		addParsingException(new BadArgException(UNKNOWN_ARGUMENT, tokenPosition, "Unknown argument '" + specifiedArgName.getDisplayName() + "'", specifiedArgName));
	}

	void foundUnknownValue(int tokenPosition, String unknownValue, BadArgException cause) throws BadArgException {
		anythingSeen = true;
		unknownArgValues.add(unknownValue);
		addParsingException(new BadArgException(UNKNOWN_ARGUMENT_VALUE, tokenPosition, "Unknown argument value '" + unknownValue + "'", cause, new ArgName(unknownValue)));
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
		List<Argument> result = new ArrayList<Argument>(arguments);
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
		List<Argument> result = new ArrayList<Argument>(arguments);
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
	public Collection<ArgName> getUnknownArgNames() {
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
		List<Argument> result = new ArrayList<Argument>(arguments);
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
		List<Argument> result = new ArrayList<Argument>(arguments);
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
	 * @param badArgException the exception to be added
	 * @throws BadArgException if the handler decides to throw the found exception
	 */
	public void addParsingException(BadArgException badArgException) throws BadArgException {
		BadArgException storeException = null;
		try {
			storeException = badArgExceptionsHandler.handle(badArgException);
		} catch (BadArgException badArgException2) {
			storeException = badArgException2;
			throw badArgException2;
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
	public List<BadArgException> getParsingExceptions() {
		return exceptions;
	}
}