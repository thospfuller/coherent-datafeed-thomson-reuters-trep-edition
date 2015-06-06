package com.coherentlogic.coherent.datafeed.exceptions;

import org.springframework.core.NestedRuntimeException;

public class SessionCreationFailedException extends NestedRuntimeException {

    private static final long serialVersionUID = 1L;

    public SessionCreationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SessionCreationFailedException(String msg) {
        super(msg);
    }
}
