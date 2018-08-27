package com.adeptions.clarguments;

import com.adeptions.clarguments.definitions.StringArgumentDefinition;
import com.adeptions.clarguments.validators.ArgumentValueValidator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArgumentDefinitionAccessorsTests {
    private static final String testName = "test";
    private static final String testDescription = "This is an argument definition description";

    @Test
    public void testArgumentDefinitionSetMandatory() throws Exception {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
        assertFalse("Default .isMandatory() should be false",
                stringArgumentDefinition.isMandatory());
        stringArgumentDefinition.makeMandatory();
        assertTrue(".isMandatory() should now be true",
                stringArgumentDefinition.isMandatory());
    }

    @Test
    public void testArgumentDefinitionAddValidator() throws Exception {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
        assertEquals(0, stringArgumentDefinition.getValueValidators().size());
        stringArgumentDefinition.addValueValidator((tokenPosition, value, argument, specifiedArgumentName) -> null);
        assertEquals(1, stringArgumentDefinition.getValueValidators().size());
    }

    @Test
    public void testArgumentDefinitionAddValidators() throws Exception {
        StringArgumentDefinition stringArgumentDefinition = new StringArgumentDefinition(testName, testDescription);
        assertEquals(0, stringArgumentDefinition.getValueValidators().size());
        ArgumentValueValidator<String> valueValidator = (tokenPosition, value, argument, specifiedArgName) -> null;
        stringArgumentDefinition.addValueValidator(valueValidator);
        assertEquals(1, stringArgumentDefinition.getValueValidators().size());
        stringArgumentDefinition.addValueValidator(valueValidator);
        assertEquals(1, stringArgumentDefinition.getValueValidators().size());
        stringArgumentDefinition.addAdditionalValueValidator(valueValidator);
        assertEquals(2, stringArgumentDefinition.getValueValidators().size());
    }
}