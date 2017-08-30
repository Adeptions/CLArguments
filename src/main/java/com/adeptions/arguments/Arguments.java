package com.adeptions.arguments;

import java.util.*;

import static com.adeptions.arguments.ArgsParsingExceptionReason.*;

public final class Arguments {
	private ArgumentDefinitions argumentDefinitions;
	private ArgsParsingOptions argsParsingOptions;
	private List<IArgument> arguments = new ArrayList<IArgument>();
	private Map<String,IArgument> specifiedArguments = new HashMap<String,IArgument>();
	private Map<String,IArgument> argumentsMap = new HashMap<String,IArgument>();
	private Map<String,IArgument> specifiedInformationals = new HashMap<String,IArgument>();
	private Map<String,IArgument> missingMandatories = new HashMap<String,IArgument>();
	private List<ArgsParsingException> exceptions = new ArrayList<ArgsParsingException>();
	private Map<String,ArgName> unknownArgNames = new HashMap<String,ArgName>();
	private List<String> unknownArgValues = new ArrayList<String>();

	Arguments(ArgumentDefinitions argumentDefinitions, ArgsParsingOptions argsParsingOptions) {
		this.argumentDefinitions = argumentDefinitions;
		this.argsParsingOptions = argsParsingOptions;
		initialize();
	}

	private void initialize() {
		for (IArgumentDefinition argumentDefinition: argumentDefinitions) {
			IArgument argument = argumentDefinition.createArgumentInstance();
			arguments.add(argument);
			argumentsMap.put(argumentDefinition.getName(), argument);
			if (argumentDefinition.isMandatory()) {
				missingMandatories.put(argumentDefinition.getName(), argument);
			}
			Iterator<String> alternativeNames = argumentDefinition.getAlternativeNames().iterator();
			while (alternativeNames.hasNext()) {
				argumentsMap.put(alternativeNames.next(), argument);
			}
		}
	}

	void foundValuedArg(IArgument argument, ArgName specifiedArgName, String rawValue) throws ArgsParsingException {
		IArgumentDefinition argumentDefinition = argument.getDefinition();
		String argumentName = argumentDefinition.getName();
		try {
			argument.setRawValue(specifiedArgName, rawValue);
			specifiedArguments.put(argumentName, argument);
			if (argumentDefinition.isMandatory()) {
				missingMandatories.remove(argumentName);
			}
		} catch (ArgsParsingException argsParsingException) {
			addParsingException(argsParsingException);
		}
	}

	void foundFlagOrInformationalArg(IArgument argument, ArgName specifiedArgName) {
		IArgumentDefinition argumentDefinition = argument.getDefinition();
		String argumentName = argumentDefinition.getName();
		argument.setSpecified();
		specifiedArguments.put(argumentName, argument);
		if (argumentDefinition.isInformational()) {
			specifiedInformationals.put(argumentName, argument);
		}
		if (argumentDefinition.isMandatory()) {
			missingMandatories.remove(argumentName);
		}
	}

	void foundUnknownArg(ArgName specifiedArgName) throws ArgsParsingException {
		unknownArgNames.put(specifiedArgName.getName(), specifiedArgName);
		addParsingException(new ArgsParsingException(UNKNOWN_ARGUMENT, "Unknown argument '" + specifiedArgName.getDisplayName() + "'", specifiedArgName));
	}

	void foundUnknownValue(String unknownValue, ArgsParsingException cause) throws ArgsParsingException {
		unknownArgValues.add(unknownValue);
		addParsingException(new ArgsParsingException(UNKNOWN_ARGUMENT_VALUE, "Unknown argument value '" + unknownValue + "'", cause, new ArgName(unknownValue)));
	}

	public IArgument get(String argumentName) {
		return argumentsMap.get(argumentName);
	}

	public boolean hasSpecifiedInformationals() {
		return specifiedInformationals.size() != 0;
	}

	public Collection<IArgument> getSpecifiedInformationals() {
		return specifiedInformationals.values();
	}

	public boolean hasMissingMandatories() {
		return missingMandatories.size() != 0;
	}

	public Collection<IArgument> getMissingMandatories() {
		return missingMandatories.values();
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

	public int getSpecifiedCount() {
		return specifiedArguments.size();
	}

	public ArgumentDefinitions getArgumentDefinitions() {
		return argumentDefinitions;
	}

	public ArgsParsingOptions getArgsParsingOptions() {
		return argsParsingOptions;
	}

	public void addParsingException(ArgsParsingException argsParsingException) throws ArgsParsingException {
		ArgsParsingException storeException = argsParsingOptions.getArgsParsingExceptionHandler().handle(argsParsingException);
		if (storeException != null) {
			exceptions.add(argsParsingException);
		}
	}

	public boolean hasParsingExceptions() {
		return exceptions.size() != 0;
	}

	public List<ArgsParsingException> getParsingExceptions() {
		return exceptions;
	}
}
