package com.adeptions.arguments;

@FunctionalInterface
public interface IArgsParsingExceptionHandler {
	ArgsParsingException handle(ArgsParsingException argsParsingException) throws ArgsParsingException;
}
