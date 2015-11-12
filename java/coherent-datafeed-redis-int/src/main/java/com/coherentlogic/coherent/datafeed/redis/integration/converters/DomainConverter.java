package com.coherentlogic.coherent.datafeed.redis.integration.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

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
    public Object fromMessage(Message<?> message, Class<?> arg1) {

        log.info("fromMessage: method begins; message: " + message);

        MarketPrice marketPrice = (MarketPrice) message.getPayload();

        log.info("marketPrice: " + marketPrice);

        return marketPrice;
    }

    @Override
    public Message<?> toMessage(Object arg0, MessageHeaders arg1) {
        throw new RuntimeException ("Operation not supported.");
    }

//    @Override
//    public <MarketPrice> Object fromMessage(Message<MarketPrice> message, Class<?> targetClass) {
//        log.info("fromMessage: method begins; message: " + message);
//
//        MarketPrice marketPrice = message.getPayload();
//
//        log.info("marketPrice: " + marketPrice);
//
//        return marketPrice;
//    }
//
//    @Override
//    public <P> Message<P> toMessage(Object object) {
//        throw new RuntimeException ("Operation not supported.");
//    }
}
