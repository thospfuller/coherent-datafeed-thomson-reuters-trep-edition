package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DAILY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MONTHLY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.YEARLY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.MessageConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.ts1.TS1Constants;

/**
 * Front-end for the Thomson Reuters market price service.
 *
 * d = historic data
 * r = session
 * s = RIC(s)
 * p = the period of the data
 * d = date
 * f = fields
 * x = historic data
 *
 * x = history(r,s)
 * x = history(r,s,p)
 * x = history(r,s,f)
 * x = history(r,s,f,p)
 * x = history(r,s,d)
 * x = history(r,s,startdate,enddate)
 * x = history(r,s,startdate,enddate,p)
 * x = history(r,s,f,startdate,enddate)
 * x = history(r,s,f,startdate,enddate,p)
 *
 * @see {@link com.reuters.ts1.TS1Constants#MONTHLY_PERIOD}
 * @see {@link com.reuters.ts1.TS1Series} The period is set here.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TimeSeriesService
    extends AsynchronousService<TimeSeries>
    implements TimeSeriesServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesService.class);

    private final Map<String, Integer> ts1ConstantsMap =
        new HashMap<String, Integer> ();

    private final CommonRequestExecutor commonRequestExecutor;

    public TimeSeriesService(
        RequestMessageBuilderFactory factory,
        Client client,
        MessageConsumer messageConsumer,
        BasicAdapter<TimeSeries, String> jsonGenerator,
        CommonRequestExecutor commonRequestExecutor
    ) {
        super(
            Constants.ELEKTRON_DD,
            RDMMsgTypes.MARKET_PRICE,
            factory,
            client,
            messageConsumer,
            jsonGenerator
        );

        this.commonRequestExecutor = commonRequestExecutor;

        ts1ConstantsMap.put(DAILY, TS1Constants.DAILY_PERIOD);
        ts1ConstantsMap.put(MONTHLY, TS1Constants.WEEKLY_PERIOD);
        ts1ConstantsMap.put(YEARLY, TS1Constants.MONTHLY_PERIOD);
    }

    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String... itemNames
    ) {
        return commonRequestExecutor.executeRequest(
            serviceName,
            loginHandle,
            msgModelType,
            itemNames
        );
    }

    /**
     * 
     * d = historic data
     * r = session
     * s = RIC(s)
     * p = the period of the data
     *
     * TS1Series.createSeries(itemName, period);
     *
     * @see {@link QueryableService}
     *
     * @todo Refactor this method so that it returns a single handle.
     */
    public Handle queryTimeSeriesFor(
        String serviceName,
        Handle loginHandle,
        String ric
    ) {
        List<Handle> results = query (serviceName, loginHandle, ric);

        return results.get(0);
    }
}
