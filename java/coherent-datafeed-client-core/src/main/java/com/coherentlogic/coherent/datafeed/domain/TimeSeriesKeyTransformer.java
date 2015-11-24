package com.coherentlogic.coherent.datafeed.domain;

import java.util.StringTokenizer;

import org.infinispan.query.Transformer;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesKeyTransformer implements Transformer {

    static final String DIVIDER = "|";

    static final int EXPECTED_TOKENS = 3;

    public TimeSeriesKeyTransformer() {
    }

    @Override
    public Object fromString(String value) {

        StringTokenizer tokenizer = new StringTokenizer(value, DIVIDER);

        int tokenCount = tokenizer.countTokens();

        if (tokenCount != EXPECTED_TOKENS)
            throw new ConversionFailedException("Unable to convert the " +
                "value: " + value);

        String serviceName = tokenizer.nextToken();

        String ric = tokenizer.nextToken();
    
        String sPeriod = tokenizer.nextToken();

        int period = Integer.valueOf (sPeriod);

        TimeSeriesKey result = new TimeSeriesKey (
            serviceName, ric, period);

        return result;
    }

    /**
     * 
     */
    @Override
    public String toString(Object object) {

        TimeSeriesKey timeSeriesKey = (TimeSeriesKey) object;

        String serviceName = timeSeriesKey.getServiceName();

        String ric = timeSeriesKey.getRic();

        int period = timeSeriesKey.getPeriod();

        StringBuilder builder = new StringBuilder ();

        builder.append(serviceName)
            .append(DIVIDER)
            .append(ric)
            .append(DIVIDER)
            .append(period);

        return builder.toString();
    }
}
