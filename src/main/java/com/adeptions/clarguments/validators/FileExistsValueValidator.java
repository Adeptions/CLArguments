/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.clarguments.validators;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;

import java.io.File;

import static com.adeptions.clarguments.ArgParsingExceptionReason.*;

/**
 * Utility value validator for checking the existence of a file for FileArgumentDefinition
 */
public class FileExistsValueValidator implements ArgumentValueValidator<File> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public File validate(int tokenPosition, File value, Argument argument, ArgName specifiedArgName) throws ArgParsingException {
		try {
			if (!value.exists()) {
				throw new ArgParsingException(INVALID_VALUE, tokenPosition, "File '" + value.getPath() + "' does not exist", argument, specifiedArgName);
			}
		} catch (SecurityException securityException) {
			throw new ArgParsingException(INVALID_VALUE, tokenPosition, "Access to file '" + value.getPath() + "' denied", securityException, argument, specifiedArgName);
		}
		return value;
	}
}