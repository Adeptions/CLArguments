package com.adeptions.arguments;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArgument<T> implements IArgument<T> {
	protected IArgumentDefinition<T> definition;
	protected boolean specified;
	protected List<T> values = new ArrayList<T>();

	public AbstractArgument(IArgumentDefinition<T> definition) {
		this.definition = definition;
	}

	public IArgumentDefinition<T> getDefinition() {
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
			return values.get(0);
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
}
