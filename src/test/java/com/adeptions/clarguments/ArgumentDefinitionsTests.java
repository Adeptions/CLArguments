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
package com.adeptions.clarguments;

import com.adeptions.clarguments.definitions.*;
import junit.framework.TestCase;

import java.util.*;

public class ArgumentDefinitionsTests extends TestCase {
	private static final String testArgumentName1 = "arg1";
	private static final String testAlternateArgumentName1 = "a1";
	private static final String testArgumentName2 = "arg2";
	private static final String testAlternateArgumentName2 = "a2";
	private static final String testArgumentName3 = "arg3";
	private static final String testArgumentName4 = "arg4";
	private static final String testArgumentName5 = "arg5";
	private static final String testArgumentName6 = "arg6";
	private static final String testDescription = "This is an argument definition description";

	private static final ArgumentDefinition argumentDefinition1 = new StringArgumentDefinition(new String[] {testArgumentName1, testAlternateArgumentName1}, testDescription).makeMandatory();
	private static final ArgumentDefinition argumentDefinition2 = new IntegerArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName2}, testDescription);
	private static final ArgumentDefinition argumentDefinition3 = new DoubleArgumentDefinition(testArgumentName3, testDescription);
	private static final ArgumentDefinition argumentDefinition4 = new FlagArgumentDefinition(testArgumentName4, testDescription);
	private static final ArgumentDefinition argumentDefinition5 = new InformationalArgumentDefinition(testArgumentName5, testDescription);
	private static final ArgumentDefinition argumentDefinition6 = new BooleanArgumentDefinition(testArgumentName6, testDescription);

	private static final Character alternateCharBetweenArgNameAndValue = ':';
	private static final String alternateArgNamePrefix = "[";
	private static final String alternateArgNameSuffix = "]";

	public void testArgumentDefinitionsConstructor() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		assertEquals(0, argumentDefinitions.size());
	}

	public void testArgumentDefinitionsConstructor2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(5, argumentDefinitions.size());
	}

	public void testArgumentDefinitionsConstructor3() throws Exception {
		List<ArgumentDefinition> argumentDefinitionList = new ArrayList<ArgumentDefinition>(Arrays.asList(new ArgumentDefinition[] {
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		}));
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(argumentDefinitionList);
		assertEquals(5, argumentDefinitions.size());
	}

	public void testArgumentDefinitionsConstructorFailsWithDuplicateNames() throws Exception {
		boolean failed = false;
		ArgumentDefinitions argumentDefinitions;
		try {
			argumentDefinitions = new ArgumentDefinitions(
					argumentDefinition1,
					argumentDefinition1
			);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgumentDefinitionsConstructorFailsWithDuplicateNames2() throws Exception {
		boolean failed = false;
		ArgumentDefinitions argumentDefinitions;
		try {
			argumentDefinitions = new ArgumentDefinitions(
					argumentDefinition1,
					new StringArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName1}, testDescription)
			);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgumentDefinitionsHasArgumentName() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		assertEquals(2, argumentDefinitions.size());
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName2));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName2));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName3));
	}

	public void testArgumentDefinitionsGetArgumentDefinitionByName() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		assertEquals(2, argumentDefinitions.size());
		assertEquals(argumentDefinition1, argumentDefinitions.getArgumentDefinitionByName(testArgumentName1));
		assertEquals(argumentDefinition1, argumentDefinitions.getArgumentDefinitionByName(testAlternateArgumentName1));
		assertEquals(argumentDefinition2, argumentDefinitions.getArgumentDefinitionByName(testArgumentName2));
		assertEquals(argumentDefinition2, argumentDefinitions.getArgumentDefinitionByName(testAlternateArgumentName2));
		assertNull(argumentDefinitions.getArgumentDefinitionByName(testArgumentName3));
	}

	public void testArgumentDefinitionsAdd() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		assertEquals(0, argumentDefinitions.size());
		argumentDefinitions.add(argumentDefinition1);
		assertEquals(1, argumentDefinitions.size());
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
	}

	public void testArgumentDefinitionsAddFailsWithDuplicateName() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(argumentDefinition1);
		assertEquals(1, argumentDefinitions.size());
		boolean failed = false;
		try {
			argumentDefinitions.add(argumentDefinition1);
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgumentDefinitionsAddFailsWithDuplicateName2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(argumentDefinition1);
		assertEquals(1, argumentDefinitions.size());
		boolean failed = false;
		try {
			argumentDefinitions.add(new StringArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName1}, testDescription));
		} catch (RuntimeException rte) {
			failed = true;
		}
		assertTrue("Should have thrown exception", failed);
	}

	public void testArgumentDefinitionsIsEmpty() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		assertTrue("Should be empty",
				argumentDefinitions.isEmpty());
		argumentDefinitions.add(argumentDefinition1);
		assertFalse("Should not be empty",
				argumentDefinitions.isEmpty());
	}

	public void testArgumentDefinitionsAddAll() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		List<ArgumentDefinition> argumentDefinitionList = new ArrayList<ArgumentDefinition>(Arrays.asList(new ArgumentDefinition[] {
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		}));
		argumentDefinitions.addAll(argumentDefinitionList);
		assertEquals(6, argumentDefinitions.size());
	}

	public void testArgumentDefinitionsIterator() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		Iterator<ArgumentDefinition> iterator = argumentDefinitions.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			assertEquals(iterator.next(), argumentDefinitions.get(index));
			index++;
		}
		assertEquals(6, index);
	}

	public void testArgumentDefinitionsHelp() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		String help = argumentDefinitions.getHelp(new ArgsParsingOptions());
		assertFalse("Help string should not be empty",
				help.isEmpty());
		String[] helpLines = help.split("\n");
		assertEquals(12, helpLines.length);
		assertTrue("Help string starts correctly",
				help.startsWith("    " + ArgsParsingOptions.DEFAULT_ARG_NAME_PREFIX + testArgumentName1 + "\n"));
		assertTrue("Help string ends correctly",
				help.endsWith("        " + testDescription));
	}

	public void testArgumentDefinitionsHelp2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		String help = argumentDefinitions.getHelp(new ArgsParsingOptions(
				alternateCharBetweenArgNameAndValue,
				alternateArgNamePrefix,
				alternateArgNameSuffix));
		assertFalse("Help string should not be empty",
				help.isEmpty());
		String[] helpLines = help.split("\n");
		assertEquals(12, helpLines.length);
		assertTrue("Help string starts correctly",
				help.startsWith("    " + alternateArgNamePrefix + testArgumentName1 + alternateArgNameSuffix + alternateCharBetweenArgNameAndValue + "string\n"));
		assertTrue("Help string ends correctly",
				help.endsWith("        " + testDescription));
	}
}