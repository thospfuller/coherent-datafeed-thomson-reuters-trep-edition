package com.coherentlogic.reuters.rfa.session;

/**
 *
    public void createSession()
    {
        String sessionName = CommandLine.variable("session");
        _mounttpi = CommandLine.booleanVariable("mounttpi");
        createPrincipals();

        if (_mounttpi)
            _session = Session.acquire(sessionName, _tokenizedPI);
        else
            _session = Session.acquire(sessionName);
        if (_session == null)
        {
            _printStream.println("Could not acquire session.");
            System.exit(1);
        }
        else
            _printStream.println("Successfully acquired session: " + sessionName);
    }
 * 
 * _marketDataSubscriber = (MarketDataSubscriber)_session
                .createEventSource(EventSource.MARKET_DATA_SUBSCRIBER, "Subscriber", false,
                                   _standardPI);
                               
 * @see MarketDataSubAppContext.java
 *
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketDataSubscriberFactory {

}
