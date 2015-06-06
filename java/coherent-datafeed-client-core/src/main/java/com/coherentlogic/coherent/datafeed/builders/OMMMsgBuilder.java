package com.coherentlogic.coherent.datafeed.builders;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.omm.OMMPriority;

public class OMMMsgBuilder<T>
    extends OMMEncoderBuilder<T>
    implements MessageBuilder {

    private final OMMPool pool;

    private OMMMsg ommMsg = null;

    public OMMMsgBuilder (OMMPool pool, OMMEncoder encoder) {
        super (encoder);
        this.pool = pool;
    }

    public OMMPool getOMMPool() {
        return pool;
    }

    public T encodeMsgInit (
        short attribDataType,
        short dataType
    ) {
        encodeMsgInit(ommMsg, attribDataType, dataType);
        return (T) this;
    }

    /**
     * This method must be called before any other method are invoked.
     */
    public T createOMMMsg () {
        this.ommMsg = pool.acquireMsg();
        return (T) this;
    }

    /**
     * Delegate method to pool.acquireAttribInfo.
     *
     * @return An instance of {@link OMMAttribInfo}.
     */
    public OMMAttribInfo acquireAttribInfo () {
        return pool.acquireAttribInfo();
    }

    /**
     * Delegate method to pool.releaseAttribInfo.
     */
    public void releaseAttribInfo (OMMAttribInfo attribInfo) {
        pool.releaseAttribInfo(attribInfo);
    }

    /**
     * 
     * @param msgType One of the OMMMsg.MsgType constants.
     *
     * @see OMMMsg.MsgType
     *
     * @todo Check that ommMsg is not null.
     */
    public T setMsgType (byte msgType) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setMsgType(msgType);
        return (T) this;
    }

    public T setMsgModelType (short msgModelType) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setMsgModelType(msgModelType);
        return (T) this;
    }

    public T setIndicationFlags (int flags) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setIndicationFlags(flags);
        return (T) this;
    }

    /**
     * 
     * @param serviceName
     * @param nameType
     * @param itemNames
     * @return
     *
     * @todo itemNames needs to be gt zero and there should be a check for this.
     */
    public T setAttribInfo (
        String serviceName,
        short nameType,
        String... itemNames
    ) {
        return (T) setAttribInfo (
            serviceName,
            nameType,
            (Integer) null,
            itemNames
        );
    }

    /**
     * 
     * @param serviceName
     * @param nameType
     * @param itemNames
     * @return
     *
     * @todo itemNames needs to be gt zero and there should be a check for this.
     */
    public T setAttribInfo (
        String serviceName,
        short nameType,
        Integer filter,
        String... itemNames
    ) {
        /* Note that prior to removing the ServiceName (enum) the method that
         * took a ServiceName (juxtaposed with a String) performed a check, as
         * is below, where if the serviceName is null an exception is thrown.
         *
         * Once we removed the ServiceName enum, that method was no longer
         * necessary and hence we only have this method.
         *
         * Since authentication does not require a serviceName we're removing
         * the assertNotNull check from below, which means a unit test has been
         * disabled as well.
         */
//        assertNotNull ("serviceName", serviceName);

        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");

        if (itemNames == null || itemNames.length == 0)
            throw new MissingDataException(
                "The itemNames parameter is either null or empty.");

        OMMAttribInfo attribInfo = pool.acquireAttribInfo();

        attribInfo.setName(serviceName);
        attribInfo.setNameType(nameType);

        if (filter != null)
            attribInfo.setFilter(filter);

        for (String itemName : itemNames)
            ommMsg.setAttribInfo(serviceName, itemName, nameType);

        return (T) this;
    }

    public T setAttribInfo (OMMAttribInfo attribInfo) {
        ommMsg.setAttribInfo(attribInfo);

        return (T) this;
    }

    public T setPriority (OMMPriority priority) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setPriority(priority);
        return (T) this;
    }

    public T setPriority (byte priorityClass, int priorityCount) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setPriority(priorityClass, priorityCount);
        return (T) this;
    }

    public T setAssociatedMetaInfo (Handle handle) {
        assertNotNull (
            "ommMsg",
            ommMsg,
            "Did you forget to invoke the createOMMMsg method?");
        ommMsg.setAssociatedMetaInfo(handle);
        return (T) this;
    }

    /**
     * @see OMMPool#releaseMsg(OMMMsg)
     */
    public T releaseMsg () {
        pool.releaseMsg(ommMsg);
        return (T) this;
    }

    /**
     * Method gets the encoded object, releases the original ommMsg from the
     * pool, and then returns the encoded message. 
     */
    @Override
    public OMMMsg getMessage() {

        OMMMsg result = (OMMMsg) getOMMEncoder().getEncodedObject();

        pool.releaseMsg(ommMsg);

        return result;
    }

    /**
     * Method returns the unencoded message; note that this message will not be
     * released after this method returns.
     *
     * @return The current OMMMsg.
     */
    public OMMMsg getUnencodedMessage () {
        return ommMsg;
    }

    /**
     * Setter method for the ommMsg. The user should be creating the ommMsg via
     * the {@link #createOMMMsg()} method, however we have the setter method
     * for testing purposes as it allows us to pass in a mock object from the
     * unit test.
     * @param ommMsg
     */
    void setOmmMsg(OMMMsg ommMsg) {
        this.ommMsg = ommMsg;
    }
}
