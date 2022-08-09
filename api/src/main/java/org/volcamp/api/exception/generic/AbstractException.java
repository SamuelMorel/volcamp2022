package org.volcamp.api.exception.generic;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class AbstractException extends RuntimeException {
    public static final Throwable CAUSE = null;
    public static final boolean ENABLE_SUPPRESSION = false;
    public static final boolean WRITABLE_STACK_TRACE = false;

    public AbstractException(String message, Object... parameters) {
        super(String.format(message, parameters), CAUSE, ENABLE_SUPPRESSION, WRITABLE_STACK_TRACE);
        log.error(super.getMessage());
    }
}
