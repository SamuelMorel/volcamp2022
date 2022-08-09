package org.volcamp.api.exception;

/**
 * This exception is thrown when multiple object where found and only one was expected
 */
public class MultipleResultsFound extends BadRequestException {
    public MultipleResultsFound() {
        super("Multiple results found");
    }
}
