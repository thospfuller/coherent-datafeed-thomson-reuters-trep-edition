package com.coherentlogic.coherent.datafeed.adapters.json;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TimeSeriesJSONGenerator
    extends AbstractGJSONGenerator<TimeSeries> {

    static Gson newGson () {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(
            TimeSeries.class,
            new TimeSeriesSerializer()
        );
        // Not to be used in production.
        //gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        return gson;
    }

    public TimeSeriesJSONGenerator() {
        this (newGson ());
    }

    public TimeSeriesJSONGenerator(Gson gson) {
        super(gson);
    }

    @Override
    public String adapt(TimeSeries timeSeries) {

        Gson gson = getGson();

        String result = gson.toJson(timeSeries);

        return result;
    }

    public static void main (String[] unused) {
        BasicAdapter<TimeSeries, String> timeSeriesJSONGenerator
            = new TimeSeriesJSONGenerator();

        TimeSeries timeSeries = new TimeSeries ();

        timeSeries.addHeader("foo", "bar", "baz");

        long time = System.currentTimeMillis();

        Sample sample1 = new Sample (time);

        sample1.addPoint("A", "B", "C");

        Sample sample2 = new Sample (time);

        sample2.addPoint("D", "E", "F");

        Sample sample3 = new Sample (time);

        sample3.addPoint("G", "H", "I");

        timeSeries.addSample(sample1, sample2, sample3);

        String result = timeSeriesJSONGenerator.adapt(timeSeries);

        System.out.println ("result: " + result);
    }
}
