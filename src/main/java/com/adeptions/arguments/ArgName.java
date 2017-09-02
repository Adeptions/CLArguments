package com.adeptions.arguments;

import static com.adeptions.arguments.ArgParsingExceptionReason.*;

public class ArgName {
	private String raw;
	private String name;
	private String displayName;
	private String value;

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

	public boolean hasValue() {
		return value != null;
	}

	public String getValue() {
		return value;
	}

	static ArgName createFromSpacedArg(String arg, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		String argNamePrefix = argsParsingOptions.getArgNamePrefix();
		String argNameSuffix = argsParsingOptions.getArgNameSuffix();
		ArgName result = new ArgName(arg);
		String name = arg;
		try {
			if (argNamePrefix != null) {
				if (name.startsWith(argNamePrefix)) {
					name = name.substring(argNamePrefix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_PREFIX, "Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if (argNameSuffix != null) {
				if (name.endsWith(argNameSuffix)) {
					name = name.substring(0, name.length() - argNameSuffix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_SUFFIX, "Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgParsingException argsParsingException) {
			throw argsParsingException;
		} catch (Exception ex) {
			throw new ArgParsingException(UNEXPECTED_EXCEPTION, "Unknown error parsing argument name", ex, result);
		}
		return result;
	}

	static ArgName createFromNonSpacedArg(String arg, ArgsParsingOptions argsParsingOptions) throws ArgParsingException {
		String argNamePrefix = argsParsingOptions.getArgNamePrefix();
		String argNameSuffix = argsParsingOptions.getArgNameSuffix();
		Character argNameAndValueSeparator = argsParsingOptions.getCharacterBetweenArgNameAndValue();
		ArgName result = new ArgName(arg);
		String name = arg;
		int separatorAt;
		try {
			if (argNamePrefix != null) {
				if (name.startsWith(argNamePrefix)) {
					name = name.substring(argNamePrefix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_PREFIX, "Argument names must begin with '" + argNamePrefix + "'", result);
				}
			}
			if ((separatorAt = name.indexOf(argNameAndValueSeparator)) != -1) {
				result.value = name.substring(separatorAt + 1);
				name = name.substring(0, separatorAt);
			}
			if (argNameSuffix != null) {
				if (name.endsWith(argNameSuffix)) {
					name = name.substring(0, name.length() - argNameSuffix.length());
				} else {
					throw new ArgParsingException(INVALID_ARGUMENT_NAME_SUFFIX, "Argument names must end with '" + argNameSuffix + "'", result);
				}
			}
			result.name = name;
			result.displayName = (argNamePrefix != null ? argNamePrefix : "") +
					name +
					(argNameSuffix != null ? argNameSuffix : "");
		} catch (ArgParsingException argsParsingException) {
			throw argsParsingException;
		} catch (Exception ex) {
			throw new ArgParsingException(UNEXPECTED_EXCEPTION, "Unknown error parsing argument name", ex, result);
		}
		return result;
	}
}
