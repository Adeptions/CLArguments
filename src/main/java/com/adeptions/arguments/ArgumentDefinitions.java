package com.adeptions.arguments;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static com.adeptions.arguments.ArgsParsingExceptionReason.*;

public class ArgumentDefinitions implements List<IArgumentDefinition> {
	protected List<IArgumentDefinition> definitions = new ArrayList<IArgumentDefinition>();
	protected Map<String,IArgumentDefinition> definitionsNameMap = new HashMap<String, IArgumentDefinition>();

	public ArgumentDefinitions() throws ArgumentDefinitionException {
	}

	public ArgumentDefinitions(IArgumentDefinition... argumentDefinitions) throws ArgumentDefinitionException {
		addAll(Arrays.asList(argumentDefinitions));
	}

	public ArgumentDefinitions(List<IArgumentDefinition> argumentDefinitions) throws ArgumentDefinitionException {
		addAll(argumentDefinitions);
	}

	public boolean hasArgumentName(String argumentName) {
		return definitionsNameMap.containsKey(argumentName);
	}

	public IArgumentDefinition getArgumentDefinitionByName(String argumentName) {
		return definitionsNameMap.get(argumentName);
	}

	public String getHelp(ArgsParsingOptions argsParsingOptions) {
		StringBuilder builder = new StringBuilder();
		Iterator<IArgumentDefinition> iterator = definitions.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().getHelp(argsParsingOptions))
					.append(iterator.hasNext() ? "\n" : "");
		}
		return builder.toString();
	}

	public Arguments parseArgs(String[] args) throws ArgsParsingException {
		return parseArgs(args, null);
	}

	public Arguments parseArgs(String[] args, ArgsParsingOptions argsParsingOptions) throws ArgsParsingException {
		ArgsParsingOptions useArgsParsingOptions = (argsParsingOptions == null ? new ArgsParsingOptions() : argsParsingOptions);
		Arguments result = new Arguments(this, useArgsParsingOptions);
		if (useArgsParsingOptions.isSpaceBetweenArgNameAndValue()) {
			parseSpaceBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		} else {
			parseCharBetweenArgNameAndValue(args, result, useArgsParsingOptions);
		}
		if (!result.hasSpecifiedInformationals() && result.hasMissingMandatories()) {
			Collection<IArgument> missingMandatories = result.getMissingMandatories();
			if (useArgsParsingOptions.isThrowImmediateParsingExceptions()) {
				StringBuilder exceptionMessageBuilder = new StringBuilder(missingMandatories.size() > 1 ? "The following mandatory arguments were missing:-\n    " : "The following mandatory argument was missing:-\n    ");
				Iterator<IArgument> iterator = missingMandatories.iterator();
				while (iterator.hasNext()) {
					exceptionMessageBuilder.append(iterator.next().getDefinition().getDisplayName(useArgsParsingOptions))
							.append(iterator.hasNext() ? ", " : "");
				}
				throw new ArgsParsingException(MISSING_MANDATORIES, exceptionMessageBuilder.toString());
			} else {
				for (IArgument argument: missingMandatories) {
					result.addParsingException(new ArgsParsingException(MISSING_MANDATORY, "Missing mandatory argument: " + argument.getDefinition().getDisplayName(useArgsParsingOptions), argument));
				}
			}
		}
		return result;
	}

	private static void parseSpaceBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) throws ArgsParsingException {
		ListIterator<String> iterator = Arrays.asList(args).listIterator();
		while (iterator.hasNext()) {
			String arg = iterator.next();
			ArgName argName = ArgName.parseSpacedArgNameFromArg(arg, argsParsingOptions);
			if (argName.isOk()) {
				IArgument argument = arguments.get(argName.name);
				if (argument != null) {
					if (argument.getDefinition().isValued()) {
						if (!iterator.hasNext()) {
							generateArgsParsingException(MISSING_VALUE, "Missing value for argument '" + argName.displayName + "'", arguments, argsParsingOptions, argument, argName);
						} else {
							String value = iterator.next();
							if (checkValueIsNotArgName(value, arguments, argsParsingOptions)) {
								arguments.foundValuedArg(argument, argName, value);
							} else {
								// the value turned out to be an arg name
								// step backwards so that loop gets the next arg...
								iterator.previous();
								generateArgsParsingException(MISSING_VALUE, "Missing value for argument '" + argName.displayName + "'", arguments, argsParsingOptions, argument, argName);
							}
						}
					} else {
						arguments.foundFlagOrInformationalArg(argument, argName);
					}
				} else {
					arguments.foundUnknownArg(argName);
				}
			} else {
				arguments.foundUnknownValue(argName);
			}
		}
	}

	private static boolean checkValueIsNotArgName(String value, Arguments arguments, ArgsParsingOptions argsParsingOptions) {
		boolean result = true;
		// trap any exceptions that ArgName may throw...
		try {
			ArgName argName = ArgName.parseSpacedArgNameFromArg(value, argsParsingOptions);
			result = !(argName.isOk() && arguments.get(argName.name) != null);
		} catch (ArgsParsingException argsParsingException) {
			// an exception means it's definitely not an arg name...
			result = true;
		}
		return result;
	}

	private static void parseCharBetweenArgNameAndValue(String[] args, Arguments arguments, ArgsParsingOptions argsParsingOptions) {
		// TODO - the hard work!
		throw new NotImplementedException();
	}

	static void generateArgsParsingException(ArgsParsingExceptionReason reason, String message, Arguments arguments, ArgsParsingOptions argsParsingOptions, IArgument argument, ArgName specifiedArgName) throws ArgsParsingException {
		ArgsParsingException argsParsingException = new ArgsParsingException(reason, message, argument, specifiedArgName);
		if (argsParsingOptions.isThrowImmediateParsingExceptions()) {
			throw argsParsingException;
		} else {
			arguments.addParsingException(argsParsingException);
		}
	}

	public int size() {
		return definitions.size();
	}

	public boolean isEmpty() {
		return definitions.isEmpty();
	}

	public boolean contains(Object o) {
		return definitions.contains(o);
	}

	public Iterator<IArgumentDefinition> iterator() {
		return definitions.iterator();
	}

	public Object[] toArray() {
		return definitions.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return definitions.toArray(a);
	}

	public boolean add(IArgumentDefinition argumentDefinition) {
		addNames(argumentDefinition);
		return definitions.add(argumentDefinition);
	}

	public boolean remove(Object o) {
		boolean result = definitions.remove(o);
		if (result) {
			removeNames((IArgumentDefinition)o);
		}
		return result;
	}

	public boolean containsAll(Collection<?> c) {
		return definitions.containsAll(c);
	}

	public boolean addAll(Collection<? extends IArgumentDefinition> c) {
		for (IArgumentDefinition argumentDefinition: c) {
			addNames(argumentDefinition);
		}
		return definitions.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends IArgumentDefinition> c) {
		for (IArgumentDefinition argumentDefinition: c) {
			addNames(argumentDefinition);
		}
		return definitions.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		for (Object o: c) {
			if (o != null && o instanceof IArgumentDefinition) {
				removeNames((IArgumentDefinition)o);
			}
		}
		return definitions.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		for (IArgumentDefinition argumentDefinition: definitions) {
			if (!c.contains(argumentDefinition)) {
				removeNames(argumentDefinition);
			}
		}
		return definitions.retainAll(c);
	}

	public void clear() {
		definitionsNameMap.clear();
		definitions.clear();
	}

	public IArgumentDefinition get(int index) {
		return definitions.get(index);
	}

	public IArgumentDefinition set(int index, IArgumentDefinition argumentDefinition) {
		removeNames(definitions.get(index));
		addNames(argumentDefinition);
		return definitions.set(index, argumentDefinition);
	}

	public void add(int index, IArgumentDefinition argumentDefinition) {
		addNames(argumentDefinition);
		definitions.add(index, argumentDefinition);
	}

	public IArgumentDefinition remove(int index) {
		removeNames(definitions.get(index));
		return definitions.remove(index);
	}

	public int indexOf(Object o) {
		return definitions.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return definitions.lastIndexOf(o);
	}

	public ListIterator<IArgumentDefinition> listIterator() {
		return definitions.listIterator();
	}

	public ListIterator<IArgumentDefinition> listIterator(int index) {
		return definitions.listIterator(index);
	}

	public List<IArgumentDefinition> subList(int fromIndex, int toIndex) {
		return definitions.subList(fromIndex, toIndex);
	}

	private void addNames(IArgumentDefinition argumentDefinition) {
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

	private void addName(String name, IArgumentDefinition argumentDefinition) {
		definitionsNameMap.put(name, argumentDefinition);
	}

	private void removeNames(IArgumentDefinition argumentDefinition) {
		definitionsNameMap.remove(argumentDefinition.getName());
		for (Object name: argumentDefinition.getAlternativeNames()) {
			removeName((String)name);
		}
	}

	private void removeName(String name) {
		definitionsNameMap.remove(name);
	}
}
