package com.adeptions.arguments;

import java.util.*;

public abstract class AbstractArgument<T> implements Argument<T> {
	protected Arguments parentArguments;
	protected ArgumentDefinition<T> definition;
	protected boolean specified;
	protected List<T> values = new ArrayList<T>();
	protected List<String> invalidValues = new ArrayList<String>();

	public AbstractArgument(ArgumentDefinition<T> definition) {
		this.definition = definition;
	}

	public ArgumentDefinition<T> getDefinition() {
		return definition;
	}

	public boolean isSpecified() {
		return specified;
	}

	public void setSpecified() {
		specified = true;
	}

	public T getValue() {
		if (specified) {
			return values.size() > 0 ? values.get(0) : null;
		} else if (definition.hasDefaultValue()) {
			return definition.getDefaultValue();
		}
		return null;
	}

	public void addValue(T value) {
		specified = true;
		values.add(value);
	}

	public List<T> getAllValues() {
		return values;
	}

	public Arguments getParentArguments() {
		return parentArguments;
	}

	public void setParentArguments(Arguments parentArguments) {
		this.parentArguments = parentArguments;
	}
}
