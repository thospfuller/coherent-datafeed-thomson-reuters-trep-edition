package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_BUFFER_SIZE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.session.omm.OMMItemEvent;
import com.reuters.ts1.TS1DefDb;

/**
 * A loader that has several methods which can be used by child classes to
 * acquire OMM data as String, byte[], etc.
 *
 * @param <E> The OMM event type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class BasicLoader<E> {

    private static final Logger log =
            LoggerFactory.getLogger(BasicLoader.class);

    private final FieldDictionary fieldDictionary;

    public BasicLoader(FieldDictionary fieldDictionary) {
        super();
        this.fieldDictionary = fieldDictionary;
    }

    /**
     * Implement this method with the logic to load the given event.
     *
     * @param event One of the OMM events.
     */
    abstract void load(E event);

    /**
     * Method retrieves the bytes for the {@link TS1DefDb} using the itemEvent
     * and row provided.
     *
     * @param itemEvent The {@link OMMItemEvent} to extract the bytes from.
     *
     * @param row The row to obtain the bytes from (ie. row64_3).
     *
     * @return An array of bytes of size {@link DEFAULT_SIZE}.
     */
    protected byte[] getDefDbBytes(OMMItemEvent itemEvent, short row) {
        OMMMsg msg = itemEvent.getMsg();

        return getDefDbBytes (msg, row);
    }

    /**
     * Method retrieves the bytes for the {@link TS1DefDb} using the msg and row
     * provided.
     *
     * @param msg The message to extract the bytes from.
     *
     * @param row The row to obtain the bytes from (ie. row64_3).
     *
     * @return An array of bytes of size {@link DEFAULT_SIZE}.
     */
    protected byte[] getDefDbBytes(OMMMsg msg, short row) {

        byte[] rowBytes = new byte[DEFAULT_BUFFER_SIZE];

        getFieldBytes(msg, row, rowBytes, 0);

        return rowBytes;
    }

    /**
     * Method copies the field data into the destination array parameter.
     */
    protected int getFieldBytes(
        OMMMsg msg,
        short fieldId,
        byte[] destination,
        int offset
    ) {
        OMMData data = getFieldData(msg, fieldId);

        int result = 0;

        if (data != null)
            result = data.getBytes(destination, offset);

        return result;
    }

    /**
     * Method finds the instance of {@link OMMData} using the specified msg and
     * fieldId.
     */
    protected OMMData getFieldData(OMMMsg msg, short fieldId) {

        log.debug("getFieldData: method begins; msg: " + msg);

        short dataType = msg.getDataType();

        OMMData result = null;

        if (dataType == OMMTypes.FIELD_LIST) {

            OMMFieldList fieldList = (OMMFieldList) msg.getPayload();

            OMMFieldEntry fieldEntry = fieldList.find(fieldId);

            result = getFieldData (fieldEntry, fieldId);
        } else {
            log.warn("OMMMsg payload must be field list -- instead it is of " +
                "type " + OMMTypes.toString(dataType) + ", (value: " +
                dataType + ").");
                //throw new IllegalArgumentException("OMMMsg payload must be
                // field list");
        }
        return result;
    }

    /**
     * Method finds the instance of {@link OMMData} using the specified
     * fieldEntry and fieldId.
     */
    protected OMMData getFieldData(OMMFieldEntry fieldEntry, short fieldId) {

        log.debug("getFieldData: method begins; msg: " + fieldEntry);

        OMMData result = null;

        if (fieldEntry != null) {

            FidDef fidDef = fieldDictionary.getFidDef(fieldId);

            short type = fidDef.getOMMType();

            result = fieldEntry.getData(type);
        }
        return result;
    }

    /**
     * Getter method for the {@link FieldDictionary} property.
     */
    public FieldDictionary getFieldDictionary() {
        return fieldDictionary;
    }
}
//protected OMMData getFieldData(OMMMsg msg, short fid) {
//
//    log.debug("getFieldData: method begins; msg: " + msg);
//
//    short dataType = msg.getDataType();
//
//    if (dataType == OMMTypes.FIELD_LIST) {
//        
//        OMMFieldEntry fe = ((OMMFieldList) msg.getPayload()).find(fid);
//        
//        if (fe == null)
//            return null;
//
//        FidDef fidDef = fieldDictionary.getFidDef(fid);
//
//        short type = fidDef.getOMMType();
//
//        return fe.getData(type);
//    }
//    log.warn("OMMMsg payload must be field list -- instead it is of type " +
//        OMMTypes.toString(dataType) + ", (value: " + dataType + ").");
//        //throw new IllegalArgumentException("OMMMsg payload must be field
//        // list");
//
//    return null;
//}