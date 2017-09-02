package com.adeptions.arguments;

public class ArgsParsingOptions {
	public static final String DEFAULT_ARG_NAME_PREFIX = "-";
	public static final Character DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE = ':';
	private static final Character SPACE = ' ';

	protected boolean spaceBetweenArgNameAndValue;
	protected Character characterBetweenArgNameAndValue;
	protected String argNamePrefix;
	protected String argNameSuffix;
	protected ArgsParsingExceptionHandler argsParsingExceptionHandler = new ArgsParsingExceptionHandler() {
		@Override
		public ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException {
			return argsParsingException;
		}
	};

	public ArgsParsingOptions() {
		spaceBetweenArgNameAndValue = true;
		argNamePrefix = DEFAULT_ARG_NAME_PREFIX;
	}

	public ArgsParsingOptions(Character characterBetweenArgNameAndValue,
							  String argNamePrefix,
							  String argNameSuffix) {
		setCharacterBetweenArgNameAndValue(characterBetweenArgNameAndValue);
		setArgNamePrefix(argNamePrefix);
		setArgNameSuffix(argNameSuffix);
	}

	public boolean isSpaceBetweenArgNameAndValue() {
		return spaceBetweenArgNameAndValue;
	}

	public void setSpaceBetweenArgNameAndValue(boolean spaceBetweenArgNameAndValue) {
		this.spaceBetweenArgNameAndValue = spaceBetweenArgNameAndValue;
		if (this.spaceBetweenArgNameAndValue) {
			characterBetweenArgNameAndValue = null;
		} else if (characterBetweenArgNameAndValue == null) {
			characterBetweenArgNameAndValue = DEFAULT_CHAR_BETWEEN_ARG_NAME_AND_VALUE;
		}
	}

	public Character getCharacterBetweenArgNameAndValue() {
		return characterBetweenArgNameAndValue;
	}

	public void setCharacterBetweenArgNameAndValue(Character characterBetweenArgNameAndValue) {
		this.characterBetweenArgNameAndValue = (SPACE.equals(characterBetweenArgNameAndValue) ? null : characterBetweenArgNameAndValue);
		spaceBetweenArgNameAndValue = (this.characterBetweenArgNameAndValue == null);
	}

	public String getArgNamePrefix() {
		return argNamePrefix;
	}

	public void setArgNamePrefix(String argNamePrefix) {
		this.argNamePrefix = argNamePrefix == null || argNamePrefix.isEmpty() ? null : argNamePrefix;
	}

	public String getArgNameSuffix() {
		return argNameSuffix;
	}

	public void setArgNameSuffix(String argNameSuffix) {
		this.argNameSuffix = argNameSuffix == null || argNameSuffix.isEmpty() ? null : argNameSuffix;
	}

	public ArgsParsingExceptionHandler getArgsParsingExceptionHandler() {
		return argsParsingExceptionHandler;
	}

	public void setArgsParsingExceptionHandler(ArgsParsingExceptionHandler argsParsingExceptionHandler) {
		this.argsParsingExceptionHandler = argsParsingExceptionHandler;
	}
}