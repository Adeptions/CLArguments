package com.adeptions.arguments;

import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.adeptions.arguments.ArgumentDefinitionType.*;

public abstract class AbstractArgumentDefinition<T> implements IArgumentDefinition<T> {
	protected final ArgumentDefinitionType type;
	protected final String name;
	protected final String description;
	protected String format;
	protected boolean mandatory;
	protected boolean multiplesAllowed;
	protected IArgumentValueValidator valueValidator;
	private Set<String> alternativeNames = new HashSet<String>();

	AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String name, @NotNull String description) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' cannot be null on AbstractArgumentDefinition constructor");
		}
		this.type = type;
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' cannot be null on AbstractArgumentDefinition constructor");
		}
		this.name = name;
		if (description == null) {
			throw new IllegalArgumentException("Argument 'description' cannot be null on AbstractArgumentDefinition constructor");
		}
		this.description = description;
	}

	AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String[] names, @NotNull String description) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' cannot be null on AbstractArgumentDefinition constructor");
		}
		this.type = type;
		if (names.length == 0) {
			throw new ArgumentDefinitionException("Argument definition must have at least one name");
		}
		this.name = names[0];
		for (int n = 1, nmax = names.length; n < nmax; n++) {
			String alternateName = names[n];
			if (alternateName == null) {
				throw new ArgumentDefinitionException("Argument definition alternative name cannot be null");
			} else if (name.equals(alternateName) || alternativeNames.contains(alternateName)) {
				throw new ArgumentDefinitionException("Argument definition alternative name already used");
			}
			alternativeNames.add(alternateName);
		}
		if (description == null) {
			throw new IllegalArgumentException("Argument 'description' cannot be null on AbstractArgumentDefinition constructor");
		}
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName(ArgsParsingOptions argsParsingOptions) {
		Character argNamePrefix = argsParsingOptions.getArgNamePrefix();
		Character argNameSuffix = argsParsingOptions.getArgNameSuffix();
		return (argNamePrefix != null ? "" + argNamePrefix : "") +
				name +
				(argNameSuffix != null ? "" + argNameSuffix : "");
	}

	public String getDescription() {
		return description;
	}

	public String getFormat() {
		return format;
	}

	public IArgumentDefinition<T> addFormat(String format) {
		this.format = format;
		return this;
	}

	public String getHelp(ArgsParsingOptions argsParsingOptions) {
		StringBuilder builder = new StringBuilder("    ").append(getDisplayName(argsParsingOptions));
		if (!argsParsingOptions.isSpaceBetweenArgNameAndValue()) {
			String format = getFormat();
			builder.append(argsParsingOptions.getCharacterBetweenArgNameAndValue());
			if (format != null && !format.isEmpty()) {
				builder.append(format);
			} else {
				builder.append("value");
			}
		}
		if (!isMandatory()) {
			builder.append("  [optional]");
		}
		builder.append("\n        ")
				.append(getDescription());
		return builder.toString();
	}

	public T validate(T value, IArgument<T> argument, ArgName specifiedArgName) throws ArgsParsingException {
		if (valueValidator != null) {
			return (T)valueValidator.validate(value);
		}
		return value;
	}

	public ArgumentDefinitionType getType() {
		return type;
	}

	public boolean isFlag() {
		return type == FLAG;
	}

	public boolean isValued() {
		return type == VALUED;
	}

	public boolean isInformational() {
		return type == INFORMATIONAL;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean areMultiplesAllowed() {
		return multiplesAllowed;
	}

	public void setMultiplesAllowed(boolean multiplesAllowed) {
		this.multiplesAllowed = multiplesAllowed;
	}

	public Set<String> getAlternativeNames() {
		// we cannot allow accessors to alter the internal set - so return a copy...
		return new HashSet<String>(alternativeNames);
	}

	public IArgumentDefinition<T> addValidator(IArgumentValueValidator valueValidator) {
		this.valueValidator = valueValidator;
		return this;
	}

	public IArgumentDefinition<T> makeMandatory() {
		mandatory = true;
		return this;
	}

	public IArgumentDefinition<T> makeMultiplesAllowed() {
		multiplesAllowed = true;
		return this;
	}
}
