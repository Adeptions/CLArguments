package com.adeptions.arguments;

import com.sun.istack.internal.NotNull;

import java.util.*;

import static com.adeptions.arguments.ArgumentDefinitionType.*;

public abstract class AbstractArgumentDefinition<T> implements ArgumentDefinition<T> {
	protected final ArgumentDefinitionType type;
	protected final String name;
	protected final String description;
	protected String format;
	protected boolean mandatory;
	protected ArgumentValueConverter<T> valueConverter;
	protected ArgumentValueValidator<T> valueValidator;
	private Set<String> alternativeNames = new HashSet<String>();
	protected boolean hasDefaultValue;
	protected T defaultValue;

	AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String name, @NotNull String description) {
		this.type = checkType(type);
		this.name = checkName(name);
		this.description = checkDescription(description);
	}

	AbstractArgumentDefinition(@NotNull ArgumentDefinitionType type, @NotNull String[] names, @NotNull String description) {
		this.type = checkType(type);
		if (names.length == 0) {
			throw new ArgumentDefinitionException("Argument definition must have at least one name");
		}
		this.name = checkName(names[0]);
		for (int n = 1, nmax = names.length; n < nmax; n++) {
			alternativeNames.add(checkAlternativeName(names[n]));
		}
		this.description = checkDescription(description);
	}

	private ArgumentDefinitionType checkType(ArgumentDefinitionType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' cannot be null on AbstractArgumentDefinition constructor");
		}
		return type;
	}

	private String checkName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' cannot be null on AbstractArgumentDefinition constructor");
		}
		return name;
	}

	private String checkAlternativeName(String alternativeName) {
		if (alternativeName == null) {
			throw new ArgumentDefinitionException("Argument definition alternative name cannot be null");
		} else if (name.equals(alternativeName) || alternativeNames.contains(alternativeName)) {
			throw new ArgumentDefinitionException("Argument definition alternative name already used");
		}
		return alternativeName;
	}

	private String checkDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Argument 'description' cannot be null on AbstractArgumentDefinition constructor");
		}
		return description;
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

	public ArgumentDefinition<T> addFormat(String format) {
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

	public T validateValue(T value, Argument<T> argument, ArgName specifiedArgName) throws ArgParsingException {
		if (valueValidator != null) {
			return (T)valueValidator.validate(value, argument, specifiedArgName);
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

	public ArgumentValueConverter<T> getValueConverter() {
		return valueConverter;
	}

	public void setValueConverter(ArgumentValueConverter<T> valueConverter) {
		this.valueConverter = valueConverter;
	}

	public ArgumentValueValidator<T> getValueValidator() {
		return valueValidator;
	}

	public void setValueValidator(ArgumentValueValidator<T> valueValidator) {
		this.valueValidator = valueValidator;
	}

	public Set<String> getAlternativeNames() {
		// we cannot allow accessors to alter the internal set - so return a copy...
		return new HashSet<String>(alternativeNames);
	}

	public ArgumentDefinition<T> addValueConverter(ArgumentValueConverter<T> valueConverter) {
		setValueConverter(valueConverter);
		return this;
	}

	public ArgumentDefinition<T> addValueValidator(ArgumentValueValidator<T> valueValidator) {
		setValueValidator(valueValidator);
		return this;
	}

	public ArgumentDefinition<T> makeMandatory() {
		mandatory = true;
		return this;
	}


	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
		this.hasDefaultValue = true;
	}

	public ArgumentDefinition<T> addDefaultValue(T defaultValue) {
		setDefaultValue(defaultValue);
		return this;
	}
}
