package com.coherentlogic.coherent.datafeed.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * An exception that is thrown that indicates the application cannot proceed.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class FatalRuntimeException extends NestedRuntimeException {

    private static final long serialVersionUID = 1L;

    public FatalRuntimeException(String msg) {
        super(msg);
    }

    public FatalRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
