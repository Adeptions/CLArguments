package com.adeptions.arguments;

import static com.adeptions.arguments.ArgsParsingExceptionReason.*;

public class ArgName {
	private String raw;
	private String name;
	private String displayName;

	public ArgName(String raw) {
		this.raw = raw;
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
		Character argNamePrefix = argsParsingOptions.getArgNamePrefix();
		Character argNameSuffix = argsParsingOptions.getArgNameSuffix();
		ArgName result = new ArgName(arg);
		String name = arg;
		try {
			if (argNamePrefix != null) {
				if (name.startsWith("" + argNamePrefix)) {
					name = name.substring(1);
				} else {
					throw new ArgsParsingException(INVALID_ARGUMENT_NAME_PREFIX, "Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if (argNameSuffix != null) {
				if (name.endsWith("" + argNameSuffix)) {
					name = name.substring(0, name.length() - 1);
				} else {
					throw new ArgsParsingException(INVALID_ARGUMENT_NAME_SUFFIX, "Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgsParsingException argsParsingException) {
			throw argsParsingException;
		} catch (Exception ex) {
			throw new ArgsParsingException(UNEXPECTED_EXCEPTION, "Unknown error parsing argument name", ex, result);
		}
		return result;
	}
}
