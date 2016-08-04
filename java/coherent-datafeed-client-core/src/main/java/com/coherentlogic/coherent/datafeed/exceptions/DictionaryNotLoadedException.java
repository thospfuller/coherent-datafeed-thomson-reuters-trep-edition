package com.coherentlogic.coherent.datafeed.exceptions;

/**
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryNotLoadedException extends FatalRuntimeException {

    private static final long serialVersionUID = 2479729850575982941L;

    public DictionaryNotLoadedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DictionaryNotLoadedException(String msg) {
        super(msg);
    }
}
