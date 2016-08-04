package com.coherentlogic.coherent.datafeed.builders;

import com.reuters.rfa.omm.OMMMsg;

/**
 * Specification for classes that build messages that will be sent to the Elektron platform.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MessageBuilder {

    /**
     * Getter method for the message.
     *
     * @return The message that has been constructed by this builder.
     *
     * @deprecated Rename to build.
     */
    @Deprecated
    OMMMsg getMessage ();
}
