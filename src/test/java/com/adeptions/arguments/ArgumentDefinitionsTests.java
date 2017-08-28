package com.adeptions.arguments;

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

	private static final IArgumentDefinition argumentDefinition1 = new StringArgumentDefinition(new String[] {testArgumentName1, testAlternateArgumentName1}, testDescription).makeMandatory();
	private static final IArgumentDefinition argumentDefinition2 = new IntegerArgumentDefinition(new String[] {testArgumentName2, testAlternateArgumentName2}, testDescription);
	private static final IArgumentDefinition argumentDefinition3 = new DoubleArgumentDefinition(testArgumentName3, testDescription);
	private static final IArgumentDefinition argumentDefinition4 = new FlagArgumentDefinition(testArgumentName4, testDescription);
	private static final IArgumentDefinition argumentDefinition5 = new InformationalArgumentDefinition(testArgumentName5, testDescription);
	private static final IArgumentDefinition argumentDefinition6 = new BooleanArgumentDefinition(testArgumentName6, testDescription);

	private static final Character alternateCharBetweenArgNameAndValue = ':';
	private static final Character alternateArgNamePrefix = '[';
	private static final Character alternateArgNameSuffix = ']';

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
		List<IArgumentDefinition> argumentDefinitionList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
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

	public void testArgumentDefinitionsAdd2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		assertEquals(0, argumentDefinitions.size());
		argumentDefinitions.add(0, argumentDefinition1);
		assertEquals(1, argumentDefinitions.size());
		assertEquals(argumentDefinition1, argumentDefinitions.get(0));
		argumentDefinitions.add(0, argumentDefinition2);
		assertEquals(2, argumentDefinitions.size());
		assertEquals(argumentDefinition2, argumentDefinitions.get(0));
		assertEquals(argumentDefinition1, argumentDefinitions.get(1));
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

	public void testArgumentDefinitionsRemoveArgumentDefinition() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		assertEquals(2, argumentDefinitions.size());
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
		argumentDefinitions.remove(0);
		assertEquals(1, argumentDefinitions.size());
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
	}

	public void testArgumentDefinitionsClear() throws Exception {
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
		argumentDefinitions.clear();
		assertEquals(0, argumentDefinitions.size());
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName2));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName2));
	}

	public void testArgumentDefinitionsIsEmpty() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		assertTrue("Should be empty",
				argumentDefinitions.isEmpty());
		argumentDefinitions.add(argumentDefinition1);
		assertFalse("Should not be empty",
				argumentDefinitions.isEmpty());
	}

	public void testArgumentDefinitionsContains() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(argumentDefinition1);
		assertTrue("Should contain argument definition",
				argumentDefinitions.contains(argumentDefinition1));
		assertFalse("Should not contain null",
				argumentDefinitions.contains(null));
		assertFalse("Should not contain 'nope'",
				argumentDefinitions.contains("nope"));
	}

	public void testArgumentDefinitionsToArray() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		Object[] array = argumentDefinitions.toArray();
		assertEquals(2, array.length);
		assertEquals(argumentDefinition1, array[0]);
		assertEquals(argumentDefinition2, array[1]);
	}

	public void testArgumentDefinitionsToTypedArray() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		IArgumentDefinition[] array = new IArgumentDefinition[argumentDefinitions.size()];
		argumentDefinitions.toArray(array);
		assertEquals(2, array.length);
		assertEquals(argumentDefinition1, array[0]);
		assertEquals(argumentDefinition2, array[1]);
	}

	public void testArgumentDefinitionsGet() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		assertEquals(argumentDefinition1, argumentDefinitions.get(0));
		assertEquals(argumentDefinition2, argumentDefinitions.get(1));
	}

	public void testArgumentDefinitionsSet() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2
		);
		assertEquals(argumentDefinition1, argumentDefinitions.get(0));
		assertEquals(argumentDefinition2, argumentDefinitions.get(1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
		argumentDefinitions.set(0, argumentDefinition3);
		assertEquals(argumentDefinition3, argumentDefinitions.get(0));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName1));
		assertFalse("Should not contain argument name",
				argumentDefinitions.hasArgumentName(testAlternateArgumentName1));
		assertTrue("Should contain argument name",
				argumentDefinitions.hasArgumentName(testArgumentName3));
	}

	public void testArgumentDefinitionsAddAll() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions();
		List<IArgumentDefinition> argumentDefinitionList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
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

	public void testArgumentDefinitionsAddAll2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(argumentDefinition1);
		List<IArgumentDefinition> argumentDefinitionList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		}));
		argumentDefinitions.addAll(0, argumentDefinitionList);
		assertEquals(6, argumentDefinitions.size());
		assertEquals(argumentDefinition1, argumentDefinitions.get(5));
	}

	public void testArgumentDefinitionsRemoveAll() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(5, argumentDefinitions.size());
		List<IArgumentDefinition> removeList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4
		}));
		argumentDefinitions.removeAll(removeList);
		assertEquals(2, argumentDefinitions.size());
		assertEquals(argumentDefinition1, argumentDefinitions.get(0));
		assertEquals(argumentDefinition5, argumentDefinitions.get(1));
	}

	public void testArgumentDefinitionsRetainAll() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(5, argumentDefinitions.size());
		List<IArgumentDefinition> retainList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
				argumentDefinition1,
				argumentDefinition5
		}));
		argumentDefinitions.retainAll(retainList);
		assertEquals(2, argumentDefinitions.size());
		assertEquals(argumentDefinition1, argumentDefinitions.get(0));
		assertEquals(argumentDefinition5, argumentDefinitions.get(1));
	}

	public void testArgumentDefinitionsContainsAll() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5,
				argumentDefinition6
		);
		List<IArgumentDefinition> checkList = new ArrayList<IArgumentDefinition>(Arrays.asList(new IArgumentDefinition[] {
				argumentDefinition1,
				argumentDefinition2
		}));
		assertTrue("Should contain all in list",
				argumentDefinitions.containsAll(checkList));
	}

	public void testArgumentDefinitionsRemove() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(5, argumentDefinitions.size());
		assertTrue(".remove() should succeed",
				argumentDefinitions.remove(argumentDefinition1));
		assertFalse(".remove() should not succeed",
				argumentDefinitions.remove(argumentDefinition1));
		assertFalse(".remove() should not succeed",
				argumentDefinitions.remove("nope"));
		assertEquals(4, argumentDefinitions.size());
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
		Iterator<IArgumentDefinition> iterator = argumentDefinitions.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			assertEquals(iterator.next(), argumentDefinitions.get(index));
			index++;
		}
		assertEquals(6, index);
	}

	public void testArgumentDefinitionsIndexOf() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(0, argumentDefinitions.indexOf(argumentDefinition1));
		assertEquals(4, argumentDefinitions.indexOf(argumentDefinition5));
		assertEquals(-1, argumentDefinitions.indexOf(null));
		assertEquals(-1, argumentDefinitions.indexOf("nope"));
	}

	public void testArgumentDefinitionsLastIndexOf() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		assertEquals(0, argumentDefinitions.lastIndexOf(argumentDefinition1));
		assertEquals(4, argumentDefinitions.lastIndexOf(argumentDefinition5));
		assertEquals(-1, argumentDefinitions.lastIndexOf(null));
		assertEquals(-1, argumentDefinitions.lastIndexOf("nope"));
	}

	public void testArgumentDefinitionsSubList() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		List<IArgumentDefinition> subList = argumentDefinitions.subList(1, 4);
		assertEquals(3, subList.size());
		assertEquals(argumentDefinition2, subList.get(0));
		assertEquals(argumentDefinition3, subList.get(1));
		assertEquals(argumentDefinition4, subList.get(2));
	}

	public void testArgumentDefinitionsListIterator() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		ListIterator<IArgumentDefinition> iterator = argumentDefinitions.listIterator();
		int index = 0;
		while (iterator.hasNext()) {
			assertEquals(iterator.next(), argumentDefinitions.get(index));
			index++;
		}
		assertEquals(5, index);
	}

	public void testArgumentDefinitionsListIterator2() throws Exception {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				argumentDefinition1,
				argumentDefinition2,
				argumentDefinition3,
				argumentDefinition4,
				argumentDefinition5
		);
		int index = 1;
		ListIterator<IArgumentDefinition> iterator = argumentDefinitions.listIterator(index);
		while (iterator.hasNext()) {
			assertEquals(iterator.next(), argumentDefinitions.get(index));
			index++;
		}
		assertEquals(5, index);
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
