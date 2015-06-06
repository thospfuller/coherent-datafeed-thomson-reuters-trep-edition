package com.coherentlogic.coherent.datafeed.redis.integration.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.converter.MessageConverter;

/**
 * A converter that extracts the {@link MarketPrice} from the payload and
 * returns the result.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DomainConverter implements MessageConverter {

    private static final Logger log =
        LoggerFactory.getLogger(DomainConverter.class);

    public DomainConverter() {
    }

    @Override
    public <MarketPrice> Object fromMessage(Message<MarketPrice> message) {
        log.info("fromMessage: method begins; message: " + message);

        MarketPrice marketPrice = message.getPayload();

        log.info("marketPrice: " + marketPrice);

        return marketPrice;
    }

    @Override
    public <P> Message<P> toMessage(Object object) {
        throw new RuntimeException ("Operation not supported.");
    }
}
