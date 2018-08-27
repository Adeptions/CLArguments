package com.adeptions.clarguments.definitions;

import com.adeptions.clarguments.ArgumentDefinitionType;
import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;
import com.adeptions.clarguments.arguments.FileArgument;

import java.io.File;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.MISSING_VALUE;

/**
 * Represents the definition for a File valued argument
 */
public class FileArgumentDefinition extends AbstractArgumentDefinition<File> implements ArgumentDefinition<File> {
    /**
     * Constructs a DoubleArgumentDefinition with the specified name and description
     * @param name the name of the argument
     * @param description the description of the argument
     */
    public FileArgumentDefinition(String name, String description) {
        super(ArgumentDefinitionType.VALUED, name, description);
        addValueFormat("number");
    }

    /**
     * Constructs a DoubleArgumentDefinition with the specified names and description
     * @param names the names of the argument
     * @param description the description of the argument
     */
    public FileArgumentDefinition(String[] names, String description) {
        super(ArgumentDefinitionType.VALUED, names, description);
        addValueFormat("number");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File convertRawValue(int tokenPosition, String rawValue, Argument<File> argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        if (valueConverter != null) {
            return valueConverter.convert(tokenPosition, rawValue, argument, specifiedArgumentName);
        }
        try {
            return new File(rawValue);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new BadArgumentException(MISSING_VALUE, tokenPosition, "Value missing", ex, argument, specifiedArgumentName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Argument<File> createArgumentInstance(Arguments parentArguments) {
        return new FileArgument(parentArguments, this);
    }
}