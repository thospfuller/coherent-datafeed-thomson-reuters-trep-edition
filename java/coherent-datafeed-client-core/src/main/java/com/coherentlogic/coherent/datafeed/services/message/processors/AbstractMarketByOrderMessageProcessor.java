package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A class which is used to process {@link MarketPrice} messages.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMarketByOrderMessageProcessor implements
    MessageProcessorSpecification<OMMItemEvent, MarketByOrder> {

}
