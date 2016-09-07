package com.coherentlogic.coherent.datafeed.builders;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.StatusResponseBean;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationSessionException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.exceptions.TimeSeriesRequestFailedException;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketByOrderServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketByPriceServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketMakerServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.WorkflowInverterService;
import com.reuters.rfa.common.Handle;

/**
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Component
public class ElektronQueryBuilder {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilder.class);

    public static final String MARKET_BY_PRICE_CACHE = "marketByPriceCache",
        MARKET_BY_ORDER_CACHE = "marketByOrderCache";

    @Autowired
    private ApplicationContext applicationContext;

    private SessionBean sessionBean;

    @Autowired
    @Qualifier(AUTHENTICATION_ENTRY_POINT)
    private AuthenticationServiceGatewaySpecification authenticationServiceGateway;

    private final WorkflowInverterService workflowInverterService = new WorkflowInverterService ();

    @Autowired
    private MarketByPriceServiceGatewaySpecification marketByPriceService;

    @Autowired
    private MarketByOrderServiceGatewaySpecification marketByOrderService;

    @Autowired
    private MarketPriceServiceGatewaySpecification marketPriceService;

    @Autowired
    private MarketMakerServiceGatewaySpecification marketMakerService;

    @Autowired
    private TimeSeriesGatewaySpecification timeSeriesService;

    @Autowired
    @Qualifier(FRAMEWORK_EVENT_LISTENER_ADAPTER)
    private FrameworkEventListenerAdapterSpecification frameworkEventListenerAdapter;

    @Autowired
    private Map<String, MarketMaker> marketMakerCache;

    @Autowired
    private Map<String, MarketPrice> marketPriceCache;

    public ElektronQueryBuilder () {
    }

    @PostConstruct
    public void initialize () {

        frameworkEventListenerAdapter.addInitialisationSuccessfulListeners (
            Arrays.asList(
                new FrameworkEventListener() {
                    @Override
                    public void onEventReceived(SessionBean session) {
                        workflowInverterService.resume(true);
                    }
                }
            )
        );

        frameworkEventListenerAdapter.addInitialisationFailedListeners (
            Arrays.asList(
                new FrameworkEventListener () {
                    @Override
                    public void onEventReceived(SessionBean session) {
                        workflowInverterService.resume(false);
                    }
                }
            )
        );
    }

    public SessionBean newSessionBean (String dacsId) {

        log.info("newSessionBean: method begins; dacsId: " + dacsId);

        SessionBean result = applicationContext.getBean(SessionBean.class);

        result.setDacsId(dacsId);

        StatusResponse statusResponse = newStatusResponse();

        result.setStatusResponse(statusResponse);

        log.info("newSessionBean: method ends; result: " + result);

        return result;
    }

    public ElektronQueryBuilder login (SessionBean sessionBean) {

        log.info("login: method begins; sessionBean: " + sessionBean);

        if (this.sessionBean == null) {

            Handle handle = authenticationServiceGateway.login(sessionBean);

            sessionBean.setHandle(handle);

            boolean result = workflowInverterService.pause();

            this.sessionBean = sessionBean;

        }

        log.info("login: method ends.");

        return this;
    }

    public ElektronQueryBuilder logout () {

        log.info("logout: method begins.");

        if (this.sessionBean == null) {
            throw new InvalidApplicationSessionException("The sessionBean is null which implies that either the "
                + "login method was never called, login failed, or logout has already been called and returned "
                + "successfully (hence the sessionBean would already be null).");
        } else {
            this.sessionBean = null;
            authenticationServiceGateway.logout();
        }

        log.info("logout: method ends.");

        return this;
    }

    public MarketPrice newMarketPrice (String ric) {

        log.info("newMarketPrice: method begins; ric: " + ric);

        MarketPrice result = marketPriceCache.get(ric);

        if (result == null) {

            result = applicationContext.getBean(MarketPrice.class);

            result.setRic(ric);

            newStatusResponse(result);
        }

        log.info("newMarketPrice: method ends: result: " + result);

        return result;
    }

    public MarketByPrice newMarketByPrice (String ric) {

        log.info("newMarketByPrice: method begins; ric: " + ric);

        Map<String, MarketByPrice> marketByPriceCache =
            (Map<String, MarketByPrice>) applicationContext.getBean(MARKET_BY_PRICE_CACHE);

        MarketByPrice result = marketByPriceCache.get(ric);

        if (result == null) {

            result = applicationContext.getBean(MarketByPrice.class);

            result.setRic(ric);

            newStatusResponse(result);
        }

        log.info("newMarketByPrice: method ends; result: " + result);

        return result;
    }

    public MarketByOrder newMarketByOrder (String ric) {

        log.info("newMarketByOrder: method begins; ric: " + ric);

        Map<String, MarketByOrder> marketByOrderCache =
            (Map<String, MarketByOrder>) applicationContext.getBean(MARKET_BY_ORDER_CACHE);

        MarketByOrder result = marketByOrderCache.get(ric);

        if (result == null) {

            result = applicationContext.getBean(MarketByOrder.class);

            result.setRic(ric);

            newStatusResponse(result);
        }

        log.info("newMarketByOrder: method ends; result: " + result);

        return result;
    }

    public MarketMaker newMarketMaker (String ric) {

        log.info("newMarketMaker: method begins; ric: " + ric);

        MarketMaker result = marketMakerCache.get(ric);

        if (result == null) {

            result = applicationContext.getBean(MarketMaker.class);

            result.setRic(ric);

            newStatusResponse(result);
        }

        log.info("newMarketMaker: method ends; result: " + result);

        return result;
    }

    protected StatusResponse newStatusResponse () {

        log.info("newStatusResponse: method begins.");

        StatusResponse result = applicationContext.getBean(StatusResponse.class);

        return result;
    }

    public StatusResponse newStatusResponse (StatusResponseBean statusResponseBean) {

        log.info("newStatusResponse: method begins; statusResponseBean: " + statusResponseBean);

        StatusResponse result = newStatusResponse();

        statusResponseBean.setStatusResponse(result);

        return result;
    }

    /**
     * Returns the time series for the given service name, ric, and period with a default timeout of 60 seconds.
     * 
     * @param serviceName For example, Constants.ELEKTRON_DD
     * @param ric The Reuters 
     * @param TS1Constants.
     * @return
     */
    public TimeSeries getTimeSeriesFor(String serviceName, String ric, int ts1ConstantsPeriod) {
        return getTimeSeriesFor(serviceName, ric, ts1ConstantsPeriod, 60, TimeUnit.SECONDS);
    }

    /**
     * 
     * @param serviceName For example, Constants.ELEKTRON_DD
     * @param ric The Reuters 
     * @param ts1ConstantsPeriod
     * @return
     */
    public TimeSeries getTimeSeriesFor(
        String serviceName,
        String ric,
        int ts1ConstantsPeriod,
        long timeout,
        TimeUnit timeUnit
    ) {
        CompletableFuture<TimeSeries> timeSeriesPromise = timeSeriesService.getTimeSeriesFor(
            serviceName,
            sessionBean,
            ric,
            ts1ConstantsPeriod
        );

        TimeSeries timeSeries;

        try {
            timeSeries = timeSeriesPromise.get(timeout, timeUnit);
        } catch (InterruptedException | ExecutionException | TimeoutException exception) {
            TimeSeriesRequestFailedException timeSeriesRequestFailedException = new TimeSeriesRequestFailedException (
                "An exception was thrown while getting the timeSeries from the timeSeriesPromise; serviceName: "
                + serviceName + ", ric: " + ric + ", ts1ConstantsPeriod: " + ts1ConstantsPeriod + ", timeout: "
                + timeout + ", timeUnit: " + timeUnit, exception);

            log.error("The request for the timeSeries with ric " + ric + " has failed.",
                timeSeriesRequestFailedException);

            throw timeSeriesRequestFailedException;
        }

        if (timeSeries == null) {
            NullPointerRuntimeException exception = new NullPointerRuntimeException (
                "The timeSeries returned is null; serviceName: " + serviceName + ", ric: " + ric
                + ", ts1ConstantsPeriod: " + ts1ConstantsPeriod + ", timeout: " + timeout + ", timeUnit: "
                + timeUnit);

                log.error("The timeSeries with ric " + ric + " was null.", exception);

                throw exception;
        }

        return timeSeries;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, MarketByPrice... marketByPrices) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketByPrices: " + marketByPrices);

        marketByPriceService.query(serviceName, sessionBean, marketByPrices);

        log.info("query: method ends.");

        return this;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, MarketByOrder... marketByOrders) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketByOrders: " + marketByOrders);

        marketByOrderService.query(serviceName, sessionBean, marketByOrders);

        log.info("query: method ends.");

        return this;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, MarketPrice... marketPrices) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketPrices: " + marketPrices);

        marketPriceService.query(serviceName, sessionBean, marketPrices);

        log.info("query: method ends.");

        return this;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, MarketMaker... marketMakers) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketMakers: " + marketMakers);

        marketMakerService.query(serviceName, sessionBean, marketMakers);

        log.info("query: method ends.");

        return this;
    }
}
