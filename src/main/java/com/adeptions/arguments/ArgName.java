package com.adeptions.arguments;

class ArgName {
	String raw;
	String name;
	String displayName;
	ArgsParsingException exception;

	private ArgName(String raw) {
		this.raw = raw;
	}

	boolean isOk() {
		return exception == null;
	}

	public String getRaw() {
		return raw;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	static ArgName parseSpacedArgNameFromArg(String arg, ArgsParsingOptions argsParsingOptions) throws ArgsParsingException {
		ArgName result = new ArgName(arg);
		String name = arg;
		Character argNamePrefix = argsParsingOptions.getArgNamePrefix();
		Character argNameSuffix = argsParsingOptions.getArgNameSuffix();
		try {
			if (argNamePrefix != null) {
				if (name.startsWith("" + argNamePrefix)) {
					name = name.substring(1);
				} else {
					throw new ArgsParsingException("Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if (argNameSuffix != null) {
				if (name.endsWith("" + argNameSuffix)) {
					name = name.substring(0, name.length() - 1);
				} else {
					throw new ArgsParsingException("Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgsParsingException argsParsingException) {
			if (argsParsingOptions.isThrowImmediateParsingExceptions()) {
				throw argsParsingException;
			}
			result.exception = argsParsingException;
		} catch (Exception ex) {
			ArgsParsingException argsParsingException = new ArgsParsingException("Unknown error parsing argument name", ex, result);
			if (argsParsingOptions.isThrowImmediateParsingExceptions()) {
				throw argsParsingException;
			}
			result.exception = argsParsingException;
		}
		return result;
	}
}
