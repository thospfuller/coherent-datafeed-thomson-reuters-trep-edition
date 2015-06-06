package com.coherentlogic.coherent.datafeed.adapters.json;


import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimeSeriesSerializer implements JsonSerializer<TimeSeries> {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesSerializer.class);

    /**
     * The index of the date in the list of samples -- should always be zero.
     */
    final static int DATE_HEADER_IDX = 0;

    /**
     * 
     * @param index The location in the *sample points* that contains the data
     *  to be added to the result.
     * @param sample
     * @param result
     */
    void toArray (
        final int index,
        final int ctr,
        String header,
        Sample sample,
        Object[] result
    ) {
        /* column will be 1 to result.size + 1 because we have to handle the
         * date, which will be stored in column 0 by default; the date is not
         * added here, however, so we'll just subtract one from the column and
         * use that value as the starting point.
         */
        List<String> points = sample.getPoints ();

        String nextPoint = points.get(index);

        result[ctr] = nextPoint;
    }

    void toArray (
        final int index,
        String header,
        List<Sample> samples,
        Object[] result
    ) {
        if("DATE".equals(header)) {
            toDateArray(index, samples, result);
        } else {

            int ctr = 0;

            for (Sample sample : samples) {
                toArray (index, (ctr++), header, sample, result);
            }
        }
    }

    void toDateArray (final int index, List<Sample> samples, Object[] result) {

        int ctr = 0;

        for (Sample sample : samples) {
            result[ctr++] = sample.getDate();
        }
    }

    @Override
    public JsonElement serialize(
        TimeSeries timeSeries,
        Type type,
        JsonSerializationContext serializationContext
    ) {
        final JsonObject jsonObject = new JsonObject();

        List<String> headers = timeSeries.getHeaders();

        List<Sample> samples = timeSeries.getSamples();

        int size = samples.size();

        Object[] result = new Object[size];

        // index zero is the date and we've already added this, so we start at
        // index one.
        int index = 0;

        for (String nextHeader : headers) {

            result = new Object[size];

            toArray ((index++), nextHeader, samples, result);

            JsonElement nextElement = serializationContext.serialize(result);

            jsonObject.add(nextHeader, nextElement);
        }
        return jsonObject;
    }
}
//Object[] toArray (Sample sample) {
//
//    List<String> points = sample.getPoints ();
//
//    int size = points.size() + 1; // Include the date too.
//
//    Object[] result = new Object[size];
//
//    result[0] = sample.getDate();
//
//    int ctr = 1;
//
//    for (String point : points) {
//        result[ctr++] = Double.valueOf (point);
//    }
//
//    return result;
//}

//for (Sample nextSample : samples) {
//    
//    final JsonElement jsonSample = 
//    
//        Object[] sampleArray = toArray (ctr, sample);
//            
////    Object[] sampleArray = toArray (sample);
////
////    final JsonElement jsonSample =
////        serializationContext.serialize(sampleArray);
////
////        jsonObject.add("r" + (ctr++), jsonSample);
//}

//Object[][] toArray (List<Sample> samples) {
//
//  List<Object[]> sampleList = new ArrayList<Object[]> (samples.size());
//
//  for (Sample sample : samples) {
//
//      Object[] sampleArray = toArray (sample);
//
//      sampleList.add(sampleArray);
//  }
//
//  int sampleSize = sampleList.size();
//
//  Object[][] result = new Object[sampleSize][];
//
//  int ctr = 0;
//
//  for (Object[] object : sampleList) {
//      result[ctr++] = object;
//  }
//  return result;
//}