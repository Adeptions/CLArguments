package com.adeptions.arguments;

import java.util.*;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public final class Arguments {
	private ArgumentDefinitions argumentDefinitions;
	private ArgsParsingOptions argsParsingOptions;
	private List<Argument> arguments = new ArrayList<Argument>();
	private Map<String,Argument> argumentsMap = new HashMap<String,Argument>();
	private Map<String,Argument> specifiedInformationals = new HashMap<String,Argument>();
	private List<ArgParsingException> exceptions = new ArrayList<ArgParsingException>();
	private Map<String,ArgName> unknownArgNames = new HashMap<String,ArgName>();
	private List<String> unknownArgValues = new ArrayList<String>();

	Arguments(ArgumentDefinitions argumentDefinitions, ArgsParsingOptions argsParsingOptions) {
		this.argumentDefinitions = argumentDefinitions;
		this.argsParsingOptions = argsParsingOptions;
		initialize();
	}

	private void initialize() {
		for (ArgumentDefinition argumentDefinition: argumentDefinitions) {
			Argument argument = argumentDefinition.createArgumentInstance();
			argument.setParentArguments(this);
			arguments.add(argument);
			argumentsMap.put(argumentDefinition.getName(), argument);
			Iterator<String> alternativeNames = argumentDefinition.getAlternativeNames().iterator();
			while (alternativeNames.hasNext()) {
				argumentsMap.put(alternativeNames.next(), argument);
			}
		}
	}

	void foundValuedArg(Argument argument, ArgName specifiedArgName, String rawValue) throws ArgParsingException {
		ArgumentDefinition argumentDefinition = argument.getDefinition();
		String argumentName = argumentDefinition.getName();
		try {
			argument.setRawValue(rawValue, specifiedArgName);
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

	void foundUnknownArg(ArgName specifiedArgName) throws ArgParsingException {
		unknownArgNames.put(specifiedArgName.getName(), specifiedArgName);
		addParsingException(new ArgParsingException(UNKNOWN_ARGUMENT, "Unknown argument '" + specifiedArgName.getDisplayName() + "'", specifiedArgName));
	}

	void foundUnknownValue(String unknownValue, ArgParsingException cause) throws ArgParsingException {
		unknownArgValues.add(unknownValue);
		addParsingException(new ArgParsingException(UNKNOWN_ARGUMENT_VALUE, "Unknown argument value '" + unknownValue + "'", cause, new ArgName(unknownValue)));
	}

	public Argument get(String argumentName) {
		return argumentsMap.get(argumentName);
	}

	public boolean hasSpecifiedInformationals() {
		return specifiedInformationals.size() != 0;
	}

	public Collection<Argument> getSpecifiedInformationals() {
		return specifiedInformationals.values();
	}

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

	public List<Argument> getMissingMandatories() {
		List<Argument> result = new ArrayList<Argument>(arguments);
		result.removeIf(argument -> argument.isSpecified() || !argument.getDefinition().isMandatory());
		return result;
	}

	public boolean hasUnknownArgNames() {
		return unknownArgNames.size() != 0;
	}

	public Collection<ArgName> getUnknownArgNames() {
		return unknownArgNames.values();
	}

	public boolean hasUnknownArgValues() {
		return unknownArgValues.size() > 0;
	}

	public List<String> getUnknownArgValues() {
		return unknownArgValues;
	}

	public int size() {
		return arguments.size();
	}

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

	public List<Argument> getSpecifiedArguments() {
		List<Argument> result = new ArrayList<Argument>(arguments);
		result.removeIf((argument -> !argument.isSpecified()));
		return result;
	}

	public ArgumentDefinitions getArgumentDefinitions() {
		return argumentDefinitions;
	}

	public ArgsParsingOptions getArgsParsingOptions() {
		return argsParsingOptions;
	}

	public void addParsingException(ArgParsingException argsParsingException) throws ArgParsingException {
		ArgParsingException storeException = argsParsingOptions.getArgsParsingExceptionHandler().handle(argsParsingException);
		if (storeException != null) {
			exceptions.add(argsParsingException);
		}
	}

	public boolean hasParsingExceptions() {
		return exceptions.size() != 0;
	}

	public List<ArgParsingException> getParsingExceptions() {
		return exceptions;
	}
}
