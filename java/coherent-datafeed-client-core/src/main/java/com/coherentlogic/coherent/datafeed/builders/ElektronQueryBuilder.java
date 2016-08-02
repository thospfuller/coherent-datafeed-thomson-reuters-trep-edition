package com.coherentlogic.coherent.datafeed.builders;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.StatusResponseBean;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketByOrderServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketMakerServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.WorkflowInverterService;
import com.reuters.rfa.common.Handle;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;

/**
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Component
public class ElektronQueryBuilder {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilder.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier(AUTHENTICATION_ENTRY_POINT)
    private AuthenticationServiceGatewaySpecification authenticationServiceGateway;

    private final WorkflowInverterService workflowInverterService = new WorkflowInverterService ();

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
    private Map<String, MarketByOrder> marketByOrderCache;

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

        Handle handle = authenticationServiceGateway.login(sessionBean);

        sessionBean.setHandle(handle);

        boolean result = workflowInverterService.pause();

        log.info("login: method ends.");

        return this;
    }

    public ElektronQueryBuilder logout (SessionBean sessionBean) {

        log.info("logout: method begins.");

        if (sessionBean == null) {
            // Either logout has already been called or we never logged in -- either way if the sessionBean is null
            // we don't do anything.
        } else {
            throw new RuntimeException("MNYI");
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

    public MarketByOrder newMarketByOrder (String ric) {

        log.info("newMarketByOrder: method begins; ric: " + ric);

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

    public ElektronQueryBuilder query (ServiceName serviceName, SessionBean sessionBean, MarketByOrder... marketByOrders) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketByOrders: " + marketByOrders);

        marketByOrderService.query(serviceName, sessionBean, marketByOrders);

        log.info("query: method ends.");

        return this;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, SessionBean sessionBean, MarketPrice... marketPrices) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketPrices: " + marketPrices);

        marketPriceService.query(serviceName, sessionBean, marketPrices);

        log.info("query: method ends.");

        return this;
    }

    public ElektronQueryBuilder query (ServiceName serviceName, SessionBean sessionBean, MarketMaker... marketMakers) {

        log.info("query: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", marketMakers: " + marketMakers);

        marketMakerService.query(serviceName, sessionBean, marketMakers);

        log.info("query: method ends.");

        return this;
    }
}
