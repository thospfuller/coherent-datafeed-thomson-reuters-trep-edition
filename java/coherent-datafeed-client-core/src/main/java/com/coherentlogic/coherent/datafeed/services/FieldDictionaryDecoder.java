package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.getSeries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;

import com.coherentlogic.coherent.datafeed.exceptions.DecodingFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMSeries;
import com.reuters.rfa.rdm.RDMDictionary;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Class is used in the integration work flow to decode dictionary data.
 *
 * @todo Unit test this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FieldDictionaryDecoder {

    private static final Logger log =
        LoggerFactory.getLogger(FieldDictionaryDecoder.class);

    private final FieldDictionary fieldDictionary;

    private final int dictionaryType;

    public FieldDictionaryDecoder(
        FieldDictionary fieldDictionary,
        int dictionaryType
    ) {
        super();
        this.fieldDictionary = fieldDictionary;
        this.dictionaryType = dictionaryType;
    }

    public Message<OMMItemEvent> decode (Message<OMMItemEvent> message) {

        OMMItemEvent itemEvent = message.getPayload();

        decode (itemEvent);

        return message;
    }

//    public Message<OMMSeries> decode (Message<OMMSeries> message) {
//
//        OMMSeries series = message.getPayload();
//
//        decode (series);
//
//        return message;
//    }

    void decode (OMMSeries series) {

        log.info("decode: method invoked; msg: " + series);

        boolean hasDataDefinitions = series.has(OMMSeries.HAS_DATA_DEFINITIONS);

        log.info("hasDataDefinitions: " + hasDataDefinitions);

        if (!hasDataDefinitions)
            throw new DecodingFailedException(
                "The dictionary with type " + dictionaryType +
                " does not have data definitions.");

        if (dictionaryType == RDMDictionary.Type.FIELD_DEFINITIONS) {
            FieldDictionary.decodeRDMFldDictionary(fieldDictionary, series);
        } else if (dictionaryType == RDMDictionary.Type.ENUM_TABLES) {
            FieldDictionary.decodeRDMEnumDictionary(fieldDictionary, series);
        }
        log.info("decode: method ends; fieldDictionary.size: " +
            fieldDictionary.size());
    }

    void decode (OMMItemEvent itemEvent) {

        log.info("decode: method invoked; itemEvent: " + itemEvent);

        OMMSeries series = getSeries(itemEvent);

        decode (series);

        log.info("decode: method ends");
    }
}
