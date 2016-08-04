package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when two or more collections are supposed to have
 * the same size.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ImbalancedCollectionsException extends FatalRuntimeException {

    private static final long serialVersionUID = 6024496464371155287L;

    public ImbalancedCollectionsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ImbalancedCollectionsException(String msg) {
        super(msg);
    }
}
