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
 * Represents a list of argument definitions and provides methods for parsing args
 */
public class ArgumentDefinitions implements Iterable<ArgumentDefinition> {
	protected List<ArgumentDefinition> definitions = new ArrayList<ArgumentDefinition>();
	protected Map<String,ArgumentDefinition> definitionsNameMap = new HashMap<String, ArgumentDefinition>();

	/**
	 * Constructs an empty ArgumentDefinitions
	 */
	public ArgumentDefinitions() {
	}

	/**
	 * Constructs an ArgumentDefinitions with the specified argument definitions
	 * @param argumentDefinitions the argument definitions
	 * @throws ArgumentDefinitionException if there was a problem with the argument definitions (e.g. a non-unique argument name)
	 */
	public ArgumentDefinitions(ArgumentDefinition... argumentDefinitions) throws ArgumentDefinitionException {
		addAll(Arrays.asList(argumentDefinitions));
	}

	/**
	 * Constructs an ArgumentDefinitions with the specified argument definitions
	 * @param argumentDefinitions the argument definitions
	 * @throws ArgumentDefinitionException if there was a problem with the argument definitions (e.g. a non-unique argument name)
	 */
	public ArgumentDefinitions(List<ArgumentDefinition> argumentDefinitions) throws ArgumentDefinitionException {
		addAll(argumentDefinitions);
	}

	/**
	 * Gets whether the arguments contains an argument with the specified name
	 * @param argumentName the argument name to be checked
	 * @return whether the arguments contains an argument with the specified name
	 */
	public boolean hasArgumentName(String argumentName) {
		return definitionsNameMap.containsKey(argumentName);
	}

	/**
	 * Gets the argument definition for a specified argument name
	 * @param argumentName the argument name
	 * @return the argument definition (or null if no definition found for the specified argument name)
	 */
	public ArgumentDefinition getArgumentDefinitionByName(String argumentName) {
		return definitionsNameMap.get(argumentName);
	}

	/**
	 * Gets a constructed help string for the argument definitions using the specified args parsing options
	 * @param argsParsingOptions the args parsing options
	 * @return the help string
	 */
	public String getHelp(ArgsParsingOptions argsParsingOptions) {
		StringBuilder builder = new StringBuilder();
		Iterator<ArgumentDefinition> iterator = definitions.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().getHelp(argsParsingOptions))
					.append(iterator.hasNext() ? "\n" : "");
		}
		return builder.toString();
	}

	/**
	 * Main method for parsing args (using default ArgsParsingOptions)
	 * @param args the args to be parsed
	 * @return the Arguments (from which values can be read)
	 * @throws ArgParsingException if there were any parsing exception found (and thrown)
	 */
	public Arguments parseArgs(String[] args) throws ArgParsingException {
		return parseArgs(args, null);
	}

	/**
	 * Main method for parsing args using the specified ArgsParsingOptions
	 * @param args the args to be parsed
	 * @param argsParsingOptions the options for parsing the args
	 * @return the Arguments (from which values can be read)
	 * @throws ArgParsingException if there were any parsing exception found (and thrown)
	 */
	public Arguments parseArgs(String[] args, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ArgsParsingOptions useArgsParsingOptions = (argsParsingOptions == null ? new ArgsParsingOptions() : argsParsingOptions);
		Arguments result = new Arguments(this, useArgsParsingOptions.getArgsParsingExceptionHandler());
		if (useArgsParsingOptions.isSpaceBetweenArgNameAndValue()) {
			parseSpaceBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		} else {
			parseCharBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		}
		checkForMissingMandatoriesAfterParsing(result, useArgsParsingOptions);
		return result;
	}

	protected void checkForMissingMandatoriesAfterParsing(Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		if (!arguments.hasSpecifiedInformationals() && arguments.hasMissingMandatories()) {
			Collection<Argument> missingMandatories = arguments.getMissingMandatories();
			for (Argument argument: missingMandatories) {
				arguments.addParsingException(new ArgParsingException(MISSING_MANDATORY, -1, "Missing mandatory argument: " + argument.getDefinition().getDisplayName(argsParsingOptions), argument));
			}
		}
	}

	private static void parseSpaceBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ListIterator<String> iterator = Arrays.asList(args).listIterator();
		while (iterator.hasNext()) {
			int tokenPosition = iterator.previousIndex() + 1;
			String arg = iterator.next();
			ArgName argName = parseSpacedArgName(tokenPosition, arg, arguments, argsParsingOptions);
			if (argName != null) {
				Argument argument = arguments.get(argName.getName());
				if (argument != null) {
					if (argument.getDefinition().isValued()) {
						if (!iterator.hasNext()) {
							generateArgsParsingException(MISSING_VALUE, tokenPosition, "Missing value for argument '" + argName.getDisplayName() + "'", arguments, argsParsingOptions, argument, argName);
						} else {
							String value = iterator.next();
							if (checkValueIsNotArgName(value, arguments, argsParsingOptions)) {
								arguments.foundValuedArg(tokenPosition, argument, argName, value);
							} else {
								// the value turned out to be an arg name
								// step backwards so that loop gets the next arg...
								iterator.previous();
								generateArgsParsingException(MISSING_VALUE, tokenPosition, "Missing value for argument '" + argName.getDisplayName() + "'", arguments, argsParsingOptions, argument, argName);
							}
						}
					} else {
						arguments.foundFlagOrInformationalArg(argument, argName);
					}
				} else {
					arguments.foundUnknownArg(tokenPosition, argName);
				}
			}
		}
	}

	private static ArgName parseSpacedArgName(int tokenPosition, String arg, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ArgName result = null;
		try {
			result = ArgName.parseFromSpacedArgToken(tokenPosition, arg, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			arguments.foundUnknownValue(tokenPosition, arg, argsParsingException);
		}
		return result;
	}

	private static boolean checkValueIsNotArgName(String value, Arguments arguments, ArgsParsingOptions argsParsingOptions) {
		boolean result = true;
		// trap any exceptions that ArgName may throw...
		try {
			ArgName argName = ArgName.parseFromSpacedArgToken(-1, value, argsParsingOptions);
			result = arguments.get(argName.getName()) == null;
		} catch (ArgParsingException argsParsingException) {
			// an exception means it's definitely not an arg name...
			result = true;
		}
		return result;
	}

	private static void parseCharBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		for (int tokenPosition = 0; tokenPosition < args.length; tokenPosition++) {
			String arg = args[tokenPosition];
			ArgName argName = parseNonSpacedArg(tokenPosition, arg, arguments, argsParsingOptions);
			if (argName != null) {
				Argument argument = arguments.get(argName.getName());
				if (argument != null) {
					ArgumentDefinition definition = argument.getDefinition();
					if (definition.isValued()) {
						// even though the argument definition says it should have a value we allow for it not having a value
						arguments.foundValuedArg(tokenPosition, argument, argName, argName.getValue());
					} else if (argName.hasValue()) {
						// non-valued (flag or informational) cannot have a value...
						generateArgsParsingException((definition.isFlag() ? FLAG_WITH_VALUE : INFORMATIONAL_WITH_VALUE), tokenPosition,
								definition.getType().getDescription() + " argument '" + argName.getDisplayName() + "' has unexpected value", arguments, argsParsingOptions, argument, argName);
					} else {
						arguments.foundFlagOrInformationalArg(argument, argName);
					}
				} else {
					arguments.foundUnknownArg(tokenPosition, argName);
				}
			}
		}
	}

	private static ArgName parseNonSpacedArg(int tokenPosition, String arg, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ArgName result = null;
		try {
			result = ArgName.parseFromNonSpacedArgToken(tokenPosition, arg, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			arguments.foundUnknownValue(tokenPosition, arg, argsParsingException);
		}
		return result;
	}

	private static void generateArgsParsingException(ArgParsingExceptionReason reason, int tokenPosition, String message, Arguments arguments, ArgsParsingOptions argsParsingOptions, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
		ArgParsingException argsParsingException = new ArgParsingException(reason, tokenPosition, message, argument, specifiedArgName);
		arguments.addParsingException(argsParsingException);
	}

	/**
	 * Gets the size of the argument definitions in the list
	 * @return the size of the argument definitions in the list
	 */
	public int size() {
		return definitions.size();
	}

	/**
	 * Gets whether the list of argument definitions is empty
	 * @return whether the list of argument definitions is empty
	 */
	public boolean isEmpty() {
		return definitions.isEmpty();
	}

	/**
	 * Adds the specified argument definition to the list
	 * @param argumentDefinition the argument definition to be added
	 * @return
	 */
	public boolean add(ArgumentDefinition argumentDefinition) {
		addNames(argumentDefinition);
		return definitions.add(argumentDefinition);
	}

	/**
	 * Adds the specified argument definitions to the list
	 * @param argumentDefinitions the argument definitions to be added
	 * @return
	 */
	public boolean addAll(Collection<? extends ArgumentDefinition> argumentDefinitions) {
		for (ArgumentDefinition argumentDefinition: argumentDefinitions) {
			addNames(argumentDefinition);
		}
		return definitions.addAll(argumentDefinitions);
	}

	/**
	 * Gets the indexed argument definition from the list
	 * @param index the index of the argument definition
	 * @return the argument definition
	 */
	public ArgumentDefinition get(int index) {
		return definitions.get(index);
	}

	private void addNames(ArgumentDefinition argumentDefinition) {
		if (definitionsNameMap.containsKey(argumentDefinition.getName())) {
			throw new ArgumentDefinitionException("Argument definitions already contains argument name '" + argumentDefinition.getName() + "'");
		}
		for (Object name: argumentDefinition.getAlternativeNames()) {
			if (definitionsNameMap.containsKey(name)) {
				throw new ArgumentDefinitionException("Argument definitions already contains argument name '" + name + "'");
			}
		}
		addName(argumentDefinition.getName(), argumentDefinition);
		for (Object name: argumentDefinition.getAlternativeNames()) {
			addName((String)name, argumentDefinition);
		}
	}

	private void addName(String name, ArgumentDefinition argumentDefinition) {
		definitionsNameMap.put(name, argumentDefinition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<ArgumentDefinition> iterator() {
		return definitions.iterator();
	}
}