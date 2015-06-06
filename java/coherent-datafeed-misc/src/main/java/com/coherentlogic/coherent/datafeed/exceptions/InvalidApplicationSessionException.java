package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application's session is not valid. To
 * be more specific this exception will be thrown when one of the properties
 * has not been set at a point in the workflow when it is needed. 
 *
 * @see {@link com.coherentlogic.coherent.datafeed.services.Session}
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidApplicationSessionException extends FatalRuntimeException {

    private static final long serialVersionUID = 2836461063245201056L;

    public InvalidApplicationSessionException(String msg) {
        super(msg);
    }

    public InvalidApplicationSessionException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
