package com.adeptions.arguments;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class ArgumentDefinitions implements Iterable<ArgumentDefinition> {
	protected List<ArgumentDefinition> definitions = new ArrayList<ArgumentDefinition>();
	protected Map<String,ArgumentDefinition> definitionsNameMap = new HashMap<String, ArgumentDefinition>();

	public ArgumentDefinitions() throws ArgumentDefinitionException {
	}

	public ArgumentDefinitions(ArgumentDefinition... argumentDefinitions) throws ArgumentDefinitionException {
		addAll(Arrays.asList(argumentDefinitions));
	}

	public ArgumentDefinitions(List<ArgumentDefinition> argumentDefinitions) throws ArgumentDefinitionException {
		addAll(argumentDefinitions);
	}

	public boolean hasArgumentName(String argumentName) {
		return definitionsNameMap.containsKey(argumentName);
	}

	public ArgumentDefinition getArgumentDefinitionByName(String argumentName) {
		return definitionsNameMap.get(argumentName);
	}

	public String getHelp(ArgsParsingOptions argsParsingOptions) {
		StringBuilder builder = new StringBuilder();
		Iterator<ArgumentDefinition> iterator = definitions.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().getHelp(argsParsingOptions))
					.append(iterator.hasNext() ? "\n" : "");
		}
		return builder.toString();
	}

	public Arguments parseArgs(String[] args) throws ArgParsingException {
		return parseArgs(args, null);
	}

	public Arguments parseArgs(String[] args, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ArgsParsingOptions useArgsParsingOptions = (argsParsingOptions == null ? new ArgsParsingOptions() : argsParsingOptions);
		Arguments result = new Arguments(this, useArgsParsingOptions);
		if (useArgsParsingOptions.isSpaceBetweenArgNameAndValue()) {
			parseSpaceBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		} else {
			parseCharBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		}
		if (!result.hasSpecifiedInformationals() && result.hasMissingMandatories()) {
			Collection<Argument> missingMandatories = result.getMissingMandatories();
			for (Argument argument: missingMandatories) {
				result.addParsingException(new ArgParsingException(MISSING_MANDATORY, "Missing mandatory argument: " + argument.getDefinition().getDisplayName(useArgsParsingOptions), argument));
			}
		}
		return result;
	}

	private static void parseSpaceBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ListIterator<String> iterator = Arrays.asList(args).listIterator();
		;
		while (iterator.hasNext()) {
			String arg = iterator.next();
			ArgName argName = parseSpacedArgName(arg, arguments, argsParsingOptions);
			if (argName != null) {
				Argument argument = arguments.get(argName.getName());
				if (argument != null) {
					if (argument.getDefinition().isValued()) {
						if (!iterator.hasNext()) {
							generateArgsParsingException(MISSING_VALUE, "Missing value for argument '" + argName.getDisplayName() + "'", arguments, argsParsingOptions, argument, argName);
						} else {
							String value = iterator.next();
							if (checkValueIsNotArgName(value, arguments, argsParsingOptions)) {
								arguments.foundValuedArg(argument, argName, value);
							} else {
								// the value turned out to be an arg name
								// step backwards so that loop gets the next arg...
								iterator.previous();
								generateArgsParsingException(MISSING_VALUE, "Missing value for argument '" + argName.getDisplayName() + "'", arguments, argsParsingOptions, argument, argName);
							}
						}
					} else {
						arguments.foundFlagOrInformationalArg(argument, argName);
					}
				} else {
					arguments.foundUnknownArg(argName);
				}
			}
		}
	}

	private static ArgName parseSpacedArgName(String arg, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		ArgName result = null;
		try {
			result = ArgName.parseSpacedArgNameFromArg(arg, argsParsingOptions);
		} catch (ArgParsingException argsParsingException) {
			arguments.foundUnknownValue(arg, argsParsingException);
		}
		return result;
	}

	private static boolean checkValueIsNotArgName(String value, Arguments arguments, ArgsParsingOptions argsParsingOptions) {
		boolean result = true;
		// trap any exceptions that ArgName may throw...
		try {
			ArgName argName = ArgName.parseSpacedArgNameFromArg(value, argsParsingOptions);
			result = arguments.get(argName.getName()) == null;
		} catch (ArgParsingException argsParsingException) {
			// an exception means it's definitely not an arg name...
			result = true;
		}
		return result;
	}

	private static void parseCharBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) {
		// TODO - the hard work!
		throw new NotImplementedException();
	}

	static void generateArgsParsingException(ArgParsingExceptionReason reason, String message, Arguments arguments, ArgsParsingOptions argsParsingOptions, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
		ArgParsingException argsParsingException = new ArgParsingException(reason, message, argument, specifiedArgName);
		arguments.addParsingException(argsParsingException);
	}

	public int size() {
		return definitions.size();
	}

	public boolean isEmpty() {
		return definitions.isEmpty();
	}

	public boolean add(ArgumentDefinition argumentDefinition) {
		addNames(argumentDefinition);
		return definitions.add(argumentDefinition);
	}

	public boolean addAll(Collection<? extends ArgumentDefinition> c) {
		for (ArgumentDefinition argumentDefinition: c) {
			addNames(argumentDefinition);
		}
		return definitions.addAll(c);
	}

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

	@Override
	public Iterator<ArgumentDefinition> iterator() {
		return definitions.iterator();
	}
}
