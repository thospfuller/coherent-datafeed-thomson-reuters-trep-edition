package com.coherentlogic.coherent.datafeed.session;

import com.reuters.rfa.common.Handle;

/**
 * DictionarySession
 * DirectorySession
 * MarketPriceSession
 * TimeSeriesSession
 * AuthenticationSession
 *
 * Construct with a login handle and a map instance for putting stuff.
 * tearDown method should remove the session from the sessionMap.
 *
 * sessionMap is used to store all sessions, the key is the handle that was
 * returned from TR when the query for data was made.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The session type.
 */
public class SessionFactory<S> {

	public SessionFactory(Handle loginHandle) {
	}

	public S getInstance () {
		return null;
	}
}
