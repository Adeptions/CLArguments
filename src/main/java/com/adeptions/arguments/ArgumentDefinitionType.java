package com.adeptions.arguments;

public enum ArgumentDefinitionType {
	VALUED("Valued"),
	FLAG("Flag"),
	INFORMATIONAL("Information");

	private String description;

	ArgumentDefinitionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
