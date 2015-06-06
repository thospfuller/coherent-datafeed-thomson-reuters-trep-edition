package com.coherentlogic.coherent.datafeed.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A service which tracks the number of RICs to load and calls the {@link
 * ConcurrentTS1DefService#setInitialized(boolean)} method once the
 * {@link #expectedSize} equals the {@link #actualSize}.
 *
 * @see {@link DictionaryLoadCompleteService}
 *
 * @deprecated This class is no longer needed.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TS1DefDbLoadCompleteService {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefDbLoadCompleteService.class);

    private final TS1DefServiceSpecification ts1DefService;

    private final int expectedSize;

    private final AtomicInteger actualSize;

    public TS1DefDbLoadCompleteService (
        TS1DefServiceSpecification ts1DefService,
        int expectedSize,
        int actualSize
    ) {
        this (
            ts1DefService,
            expectedSize,
            new AtomicInteger (
                actualSize
            )
        );
    }

    public TS1DefDbLoadCompleteService (
        TS1DefServiceSpecification ts1DefService,
        int expectedSize,
        AtomicInteger actualSize
    ) {
        this.ts1DefService = ts1DefService;
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

//    public void checkInitializationHasCompleted () {
//
//        log.info("checkInitializationHasCompleted: method begins.");
//
//        int value = actualSize.incrementAndGet();
//
//        if (value == expectedSize) {
//            log.warn ("The expected number of rics has been loaded so we " +
//                "will now call the setLoaded method on the " +
//                "ts1DefService bean; thread: " + Thread.currentThread().getName());
//            ts1DefService.setInitialized(true);
//            log.warn ("...done!");
//        } else {
//            log.info("The number of rics loaded is " + value);
//        }
//    }
}
