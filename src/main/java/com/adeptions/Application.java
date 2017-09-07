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
package com.adeptions;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.converters.*;
import com.adeptions.clarguments.definitions.*;
import com.adeptions.clarguments.validators.*;

import static com.adeptions.clarguments.PredefinedBadArgReasons.*;

public class Application {
	/**
	 * Main startup
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				new BooleanArgumentDefinition("b", "A yes/no boolean").addValueConverter(new MultiValueBooleanConverter(true, false,
						"yes|no", "y|n", "1|0", "on|off", "true|false")),
				new DoubleArgumentDefinition("n", "A number").addAdditionalValueValidator(new RangedDoubleValueValidator(1d, 10d)),
				new IntegerArgumentDefinition("i", "An integer")
						.addAdditionalValueValidator(new DisallowMultiplesValueValidator())
						.addAdditionalValueValidator(new RangedIntegerValueValidator(1, 10)),
				new FileArgumentDefinition("f", "A file"),
				new StringArgumentDefinition(new String[] {"say", "s"}, "What to say").makeMandatory().addValueValidator((tokenPosition, value, argument, specifiedArgName) -> {
					if (value == null) {
						throw new BadArgException(MISSING_VALUE, tokenPosition, "Argument '" + specifiedArgName.getDisplayName() + "' must have a value", argument);
					}
					if (((String)value).contains(" ")) {
						argument.setSpecified();
						throw new BadArgException(INVALID_VALUE, tokenPosition, "Cannot contain spaces?", argument);
					}
					return value;
				}),
				new InformationalArgumentDefinition(new String[] {"version", "v"}, "Show version"),
				new InformationalArgumentDefinition(new String[] {"help", "h"}, "Show this help")
		);
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions(':', "-", null);
		argsParsingOptions.setBadArgsExceptionHandler(new BadArgExceptionsHandler() {
			@Override
			public BadArgException handle(BadArgException badArgException) throws BadArgException {
				return badArgException;
			}
		});
		try {
			Arguments arguments = argumentDefinitions.parseArgs(args, argsParsingOptions);
			printVersionIfRequested(arguments);
			if (arguments.hasParsingExceptions()) {
				for (BadArgException badArgException: arguments.getParsingExceptions()) {
					System.err.println(badArgException.getMessage() + " (at position " + badArgException.getTokenPosition() + ")");
				}
			} else if (!arguments.anySpecified() || arguments.get("help").isSpecified()) {
				System.out.println("Help:-");
				System.out.println(argumentDefinitions.getHelp(argsParsingOptions));
			} else if (!arguments.hasSpecifiedInformationals()) {
				System.out.println("Say... " + arguments.get("say").getValue());
			}
		} catch (BadArgException e) {
			e.printStackTrace();
		}
	}

	private static void printVersionIfRequested(Arguments arguments) {
		if (arguments.get("version").isSpecified()) {
			System.out.println("Version: 1.0-SNAPSHOT");
		}
	}
}
