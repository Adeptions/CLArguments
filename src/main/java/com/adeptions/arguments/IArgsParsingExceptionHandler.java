package com.adeptions.arguments;

@FunctionalInterface
public interface IArgsParsingExceptionHandler {
	ArgParsingException handle(ArgParsingException argsParsingException) throws ArgParsingException;
}
