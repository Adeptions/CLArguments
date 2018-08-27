package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.GenericArgument;
import com.adeptions.clarguments.converters.ArgumentValueConverter;

/**
 * Represents the definition for a generic typed valued argument
 */
public class GenericArgumentDefinition<T> extends AbstractArgumentDefinition<T> implements ArgumentDefinition<T> {
    /**
     * Constructs a GenericArgumentDefinition with the specified name, description and converter
     * @param name the name of the argument
     * @param description the description of the argument
     * @param valueConverter the converter to be used to convert raw arg string values to the appropriate type
     */
    public GenericArgumentDefinition(String name, String description, ArgumentValueConverter<T> valueConverter) {
        super(ArgumentDefinitionType.VALUED, name, description);
        if (valueConverter == null) {
            throw new IllegalArgumentException("Argument 'valueConverter' cannot be null on GenericArgumentDefinition constructor");
        }
        this.valueConverter = valueConverter;
    }

    /**
     * Constructs a GenericArgumentDefinition with the specified names, description and converter
     * @param names the names of the argument
     * @param description the description of the argument
     * @param valueConverter the converter to be used to convert raw arg string values to the appropriate type
     */
    public GenericArgumentDefinition(String[] names, String description, ArgumentValueConverter<T> valueConverter) {
        super(ArgumentDefinitionType.VALUED, names, description);
        if (valueConverter == null) {
            throw new IllegalArgumentException("Argument 'valueConverter' cannot be null on GenericArgumentDefinition constructor");
        }
        this.valueConverter = valueConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T convertRawValue(int tokenPosition, String rawValue, Argument<T> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<T> createArgumentInstance(Arguments parentArguments) {
        return new GenericArgument<>(parentArguments, this);
    }
}
