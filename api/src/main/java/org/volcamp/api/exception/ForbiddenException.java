package org.volcamp.api.exception;

import io.smallrye.graphql.api.ErrorCode;

/**
 * This exception is thrown when multiple object where found and only one was expected
 */
@ErrorCode("403")
public class ForbiddenException extends BadRequestException {
    public ForbiddenException() {
        super("Forbidden");
    }
}
