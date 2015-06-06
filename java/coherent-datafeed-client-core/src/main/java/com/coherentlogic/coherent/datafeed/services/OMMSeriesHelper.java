package com.coherentlogic.coherent.datafeed.services;

import static com.reuters.rfa.rdm.RDMDictionary.Summary.DictionaryId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.UnexpectedOMMTypeException;
import com.coherentlogic.coherent.datafeed.integration.transformers.OMMSeriesTransformer;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMElementEntry;
import com.reuters.rfa.omm.OMMElementList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMNumeric;
import com.reuters.rfa.omm.OMMSeries;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.rdm.RDMDictionary;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Helper methods for working with {@link OMMSeries} objects.
 *
 * @see OMMSubAppContext.processDictionaryMsg
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMSeriesHelper {

    private static final Logger log =
        LoggerFactory.getLogger(OMMSeriesHelper.class);

    /**
     * @todo pass this in via the ctor.
     */
    private final OMMSeriesTransformer seriesTransformer;

    public OMMSeriesHelper () {
        this (new OMMSeriesTransformer());
    }
    
    public OMMSeriesHelper (OMMSeriesTransformer seriesTransformer) {
        super();
        this.seriesTransformer = seriesTransformer;
    }

    /**
     * Method extracts the dictionary type from the {@link OMMSeries} object.
     *
     * @return The dictionary type.
     *
     * @throws MissingDataException If the dictionary summary type is null.
     */
    public int getDictionaryType (OMMSeries series) {

        OMMElementList summaryData = (OMMElementList)series.getSummaryData();
        OMMElementEntry dictionarySummaryType =
            summaryData.find(RDMDictionary.Summary.Type);

        if (dictionarySummaryType == null)
            throw new MissingDataException("The series dictionary summary " +
                "type was null.");

        OMMData data = dictionarySummaryType.getData();

        OMMNumeric numericData =
            (OMMNumeric)data;

        // The toInt method has been deprecated.
        int result = (int) numericData.toLong();

        return result;
    }

    /**
     * Method obtains the series (OMMSeries) from the msg and then returns the
     * dictionary type.
     *
     * @throws MissingDataException If the dictionary summary type is null.
     */
    public int getDictionaryType (OMMMsg msg) {

        OMMSeries series = null;

        try {
            series = seriesTransformer.transformPayload (msg);
        } catch (Exception exception) {
            throw new ConversionFailedException("The OMMMsg msg could not " +
                "be converted into an OMMSeries.", exception);
        }

        return getDictionaryType (series);
    }

    public int getDictionaryType (OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg ();

        return getDictionaryType (msg);
    }

    public String findDictionaryId (OMMSeries series) {
        OMMData summaryData = series.getSummaryData();
        return findDictionaryId (summaryData);
    }

    public String findDictionaryId (OMMData summaryData) {

        short type = summaryData.getType();

        if (type != OMMTypes.ELEMENT_LIST)
            throw new UnexpectedOMMTypeException("Expected an OMMElementList " +
                "(ELEMENT_LIST) however the type was " +
                OMMTypes.toString(type));

        OMMElementList summaryDataList = (OMMElementList) summaryData;

        OMMElementEntry entry = summaryDataList.find(DictionaryId);

        short entryType = entry.getData().getType();

        log.info("entryType: " + OMMTypes.toString(entryType));

        String name = "fee";

        return name;
    }
}
