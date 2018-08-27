package com.adeptions.clarguments.arguments;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.Arguments;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.definitions.ArgumentDefinition;

import java.io.File;

/**
 * Represents a File valued argument
 */
public class FileArgument extends AbstractArgument<File> implements Argument<File> {
    /**
     * Constructs a FileArgument with the specified parent arguments and argument definition
     * @param parentArguments the arguments to which the argument belongs
     * @param definition the definition of the argument
     */
    public FileArgument(Arguments parentArguments, ArgumentDefinition<File> definition) {
        super(parentArguments, definition);
    }

    /**
     * {@inheritDoc}
     * (NB. Does not check the existing of the file or validity of the the filenpath)
     */
    @Override
    public void setRawValue(int tokenPosition, String rawValue, ArgumentName specifiedArgumentName) throws BadArgumentException {
        values.add(definition.validateValue(tokenPosition, definition.convertRawValue(tokenPosition, rawValue, this, specifiedArgumentName),this, specifiedArgumentName));
        specified = true;
    }
}
