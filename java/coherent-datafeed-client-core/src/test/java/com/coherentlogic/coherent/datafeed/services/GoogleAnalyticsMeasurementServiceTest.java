package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the {@link GoogleAnalyticsMeasurementService} class.
 *
 * Note that we do not test the {@link GoogleAnalyticsMeasurementService#fireGAFrameworkUsageEvent()} method since this
 * is better suited for an integration test.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class GoogleAnalyticsMeasurementServiceTest {

    private GoogleAnalyticsMeasurementService googleAnalyticsMeasurementService = null;

    @Before
    public void setUp() throws Exception {
        googleAnalyticsMeasurementService = new GoogleAnalyticsMeasurementService ();
    }

    @After
    public void tearDown() throws Exception {
        googleAnalyticsMeasurementService = null;
    }

    @Test
    public void testShouldTrackTrue1() {

        System.setProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY, Boolean.TRUE.toString());

        assertTrue(googleAnalyticsMeasurementService.shouldTrack());

        System.clearProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY);
    }

    @Test
    public void testShouldTrackTrue2() {

        System.clearProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY);

        assertTrue(googleAnalyticsMeasurementService.shouldTrack());
    }

    @Test
    public void testShouldTrackFalse1() {

        System.setProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY, Boolean.FALSE.toString());

        assertFalse(googleAnalyticsMeasurementService.shouldTrack());

        System.clearProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY);
    }

    @Test
    public void testShouldTrackFalse2() {

        System.setProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY, "typo");

        assertFalse(googleAnalyticsMeasurementService.shouldTrack());

        System.clearProperty(GoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY);
    }
}
