package com.coherentlogic.coherent.datafeed.builders;

import com.reuters.rfa.omm.OMMMsg;

/**
 * 
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface MessageBuilder {

    /**
     * Getter method for the message.
     *
     * @return The message that has been constructed by this builder.
     */
    OMMMsg getMessage ();
}
