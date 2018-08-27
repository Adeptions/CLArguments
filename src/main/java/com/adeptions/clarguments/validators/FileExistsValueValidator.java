package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.ArgumentName;
import com.adeptions.clarguments.BadArgumentException;
import com.adeptions.clarguments.arguments.Argument;

import java.io.File;

import static com.adeptions.clarguments.PredefinedBadArgumentReasons.INVALID_VALUE;

/**
 * Utility value validator for checking the existence of a file for FileArgumentDefinition
 */
public class FileExistsValueValidator implements ArgumentValueValidator<File> {
    /**
     * {@inheritDoc}
     */
    @Override
    public File validate(int tokenPosition, File value, Argument argument, ArgumentName specifiedArgumentName) throws BadArgumentException {
        try {
            if (!value.exists()) {
                throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                        "File '" + value.getPath() + "' does not exist", argument, specifiedArgumentName);
            }
        } catch (SecurityException securityException) {
            throw new BadArgumentException(INVALID_VALUE, tokenPosition,
                    "Access to file '" + value.getPath() + "' denied", securityException, argument, specifiedArgumentName);
        }
        return value;
    }
}