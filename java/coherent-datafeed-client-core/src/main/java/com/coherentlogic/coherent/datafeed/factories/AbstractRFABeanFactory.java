package com.coherentlogic.coherent.datafeed.factories;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * Factory class for creating {@link MarketPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractRFABeanFactory<T extends RFABean>
    implements Factory<T> {

    private final AtomicLong idCounter;

    public AbstractRFABeanFactory () {
        this (new AtomicLong(0L));
    }

    public AbstractRFABeanFactory(AtomicLong idCounter) {
        this.idCounter = idCounter;
    }

    protected T configure(T rfaBean) {

        long nextId = idCounter.getAndIncrement();

        long timestamp = System.currentTimeMillis();

        rfaBean.setUniqueId(nextId);
        rfaBean.setTimestamp(new Date (timestamp));

        return rfaBean;
    }
}
