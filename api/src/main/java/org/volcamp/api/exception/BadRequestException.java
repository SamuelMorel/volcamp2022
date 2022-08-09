package org.volcamp.api.exception;

import io.smallrye.graphql.api.ErrorCode;
import org.volcamp.api.exception.generic.AbstractException;

@ErrorCode("400")
public class BadRequestException extends AbstractException {
    public BadRequestException(String message, Object... parameters) {
        super(message, parameters);
    }
}
