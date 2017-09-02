package com.adeptions.arguments;

@FunctionalInterface
public interface ArgsParsingExceptionHandler {
	ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException;
}
