package com.adeptions.clarguments;

/**
 * Enumerator for defining the different type of argument
 *
 * <p>
 *  VALUED        - the argument has a name and value
 *  FLAG          - the argument is just a flag (has no value)
 *  INFORMATIONAL - the argument is an informational flag
 *                  (has no value and the application may want to halt normal execution and show some information when
 *                   the argument is encountered - e.g. -version or -help)
 * </p>
 */
public enum ArgumentDefinitionType {
    VALUED("Valued"),
    FLAG("Flag"),
    INFORMATIONAL("Informational");

    private String description;

    ArgumentDefinitionType(String description) {
        this.description = description;
    }

    /**
     * Gets the printable description of the type
     * @return the printable description of the type
     */
    public String getDescription() {
        return description;
    }
}