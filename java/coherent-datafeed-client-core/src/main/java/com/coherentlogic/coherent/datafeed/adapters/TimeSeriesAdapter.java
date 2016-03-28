package com.coherentlogic.coherent.datafeed.adapters;

import static com.coherentlogic.coherent.datafeed.misc.Constants.BIG_DATE;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Def;
import com.reuters.ts1.TS1DefDb;
import com.reuters.ts1.TS1Point;
import com.reuters.ts1.TS1Sample;
import com.reuters.ts1.TS1Series;

/**
 * An adapter that converts {@link TimeSeriesEntry} into an instance of
 * {@link TimeSeries}.
 *
 * @see ConsoleTSClient.java
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesAdapter extends
    AbstractAdapter<TimeSeriesEntries, TimeSeries> {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesAdapter.class);

    public static final String BEAN_NAME = "timeSeriesAdapter";

    private final TS1DefDb ts1DefDb;

    public TimeSeriesAdapter(TS1DefDb ts1DefDb) {
        super();
        this.ts1DefDb = ts1DefDb;
    }

    @Override
    public TimeSeries adapt(
        TimeSeriesEntries timeSeriesEntries
    ) {
        TimeSeries result = new TimeSeries();

        adapt(timeSeriesEntries, result);

        return result;
    }

    public void adapt(
        TimeSeriesEntries timeSeriesEntries,
        TimeSeries timeSeries
    ) {

        log.info("adapt: method begins; timeSeriesEntries: " +
            timeSeriesEntries + ", timeSeries: " + timeSeries);

        Map<Handle, TimeSeriesEntry> timeSeriesEntryMap =
            timeSeriesEntries.getTimeSeriesEntryMap();

        Set<Entry<Handle, TimeSeriesEntry>> entrySet =
            timeSeriesEntryMap.entrySet();

        boolean loaded = false;

        for (Entry<Handle, TimeSeriesEntry> nextEntry : entrySet) {

            TimeSeriesEntry timeSeriesEntry = nextEntry.getValue();

            TS1Series series = timeSeriesEntry.getTS1Series();

            if (!loaded) {
                loadHeaders(series, ts1DefDb, timeSeries);
                loaded = true;
            }

            loadSamples(series, timeSeries);
        }
    }

    /**
     * 
     * @param series
     * @param defDb
     * @param timeSeries
     */
    void loadHeaders (
        TS1Series series,
        TS1DefDb defDb,
        TimeSeries timeSeries
    ) {
        int factCount = series.getFactCount();

        String primaryRic = series.getPrimaryRic();

        log.info("primaryRic: " + primaryRic + ", factCount: " + factCount);

        timeSeries.addHeader(BIG_DATE);

        for (int ctr = 0; ctr < factCount; ctr++) {
            int fid = series.getFact(ctr);

            TS1Def def = defDb.getDef(fid);

            if (def != null) {
                String longName = def.getLongName();
                log.debug ("longName: " + longName);
                timeSeries.addHeader(longName);
            } else {
                // See the TimeSeriesLoader if this happens.

                log.warn("The def is null for the fid " + fid + "; defDb.size: " +
                    defDb.size() + "; this indicates that the defDb load " +
                    "logic has failed; setting the header to UNKNOWN" + ctr);

                //timeSeries.addHeader("UNKNOWN" + ctr);
            }
        }
    }

    void loadSamples (TS1Series series, TimeSeries timeSeries) {

        Iterator<TS1Sample> sampleIterator = series.sampleIterator();

        while (sampleIterator.hasNext()) {

            TS1Sample ts1Sample = sampleIterator.next();

            Calendar calendar = ts1Sample.getDate();

            Long time = calendar.getTimeInMillis();

            TS1Point[] points = ts1Sample.getPoints();

            loadSamples(time, points, timeSeries);
        }
    }

    private void loadSamples (
        Long date,
        TS1Point[] points,
        TimeSeries timeSeries
    ) {
        int ctr = 0;

        for (TS1Point ts1Point : points) {

            Sample sample = timeSeries.findOrCreateSample(date);

            sample.setDate(date);

            if (ts1Point.isValid()) {
                String pointValue = points[ctr].toString();

//                Point point = new Point ();
//
//                point.setValue(pointValue);

                sample.addPoint(pointValue);
//                timeSeries.addSample(sample);
            }
            ctr++;
        }
    }
}
