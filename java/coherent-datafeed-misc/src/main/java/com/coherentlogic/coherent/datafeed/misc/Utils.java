package com.coherentlogic.coherent.datafeed.misc;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.ImbalancedCollectionsException;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NonNullReferenceExpectedException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.exceptions.ValueOutOfBoundsException;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMSeries;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.session.Session;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A class which contains some static utility methods.
 * 
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class Utils {

    /**
     * Method is used to iterate over the contents of a {@link FieldDictionary}.
     *
     * For example you can pass in the nameMap, which is obtained as follows:
     *
     * Map<String, FidDef> nameMap = fieldDictionary.toNameMap();
     */
    public static void iterate (
        Map<String, FidDef> nameMap,
        Action<Map.Entry<String, FidDef>> action
    ) {
        Set<Map.Entry<String, FidDef>> nameSet = nameMap.entrySet();

        for (Map.Entry<String, FidDef> next : nameSet)
            action.execute(next);
    }

    public static void assertEquals (
        String variableName,
        long expected,
        long actual
    ) {
        if (expected != actual)
            throw new ValueOutOfBoundsException("The variable " + variableName
                + " has an actual value of " + actual
                + " however only the value " + expected + " is expected.");
    }

    public static void assertEquals (
        String leftHandSideName,
        String rightHandSideName,
        long leftHandSideValue,
        long rightHandSideValue
    ) {
        if (leftHandSideValue != rightHandSideValue)
            throw new ValueOutOfBoundsException("The value of the variable " +
                "named " + leftHandSideName + " is not equal to the value of" +
                " the variable named " + rightHandSideName + " (lhs: " +
                leftHandSideValue + ", rhs: " + rightHandSideValue + ")");
    }

    /**
     * Method which checks that the actualValue of the variable with the name
     * variableName is between the lowerBound and upperBound inclusive.
     *
     * @param variableName
     *            The variable's name.
     * @param actualValue
     *            The actual value of the variable with name variableName.
     * @param lowerBound
     *            The lower acceptable bound (inclusive).
     * @param upperBound
     *            The upper acceptable bound (inclusive).
     * 
     * @throws ValueOutOfBoundsException
     *             When the actualValue is out-of-bounds.
     */
    public static void assertBetween(String variableName, int actualValue,
            int lowerBound, int upperBound) {
        if (!(lowerBound <= actualValue && actualValue <= upperBound))
            throw new ValueOutOfBoundsException("The variable "
                    + variableName
                    + " has an actual value which is not between (inclusive) "
                    + lowerBound + " and " + upperBound + ".");
    }

    /**
     * Method throws an exception when the object is not null.
     * 
     * @param variableName The name of the variable.
     * @param object The object reference which is being checked.
     * 
     * @throws NullPointerRuntimeException When the object is null.
     */
    public static void assertNull(String variableName, Object object) {
        if (object != null)
            throw new NonNullReferenceExpectedException ("The variable named " +
                variableName + " pointed to a non-null reference (" +
                ToStringBuilder.reflectionToString(object) +
                ").");
    }

    /**
     * Method throws an exception when the object is null.
     * 
     * @param variableName The name of the variable.
     * @param object The object reference which is being checked.
     * 
     * @throws NullPointerRuntimeException When the object is null.
     */
    public static void assertNotNull(String variableName, Object object) {
        assertNotNull(variableName, object, null);
    }

    /**
     * Method throws an exception when the object is null.
     * 
     * @param variableName The name of the variable.
     * @param object The object reference which is being checked.
     * 
     * @throws NullPointerRuntimeException When the object is null.
     */
    public static void assertNotNull(String variableName, Object object, String message) {
        if (object == null) {
            String text = "The variable named " + variableName + " is null. "
                + ((message == null) ? "" : message);

            throw new NullPointerRuntimeException(text);
        }
    }

    public static void assertNotNullOrEmpty(String variableName, Object[] objects) {

        assertNotNull(variableName, objects);

        if (objects.length == 0)
            throw new MissingDataException("The array named " + variableName + " is empty.");
    }

    public static void assertNotEmpty(String variableName, Collection<?> objects) {

        if (objects.size() == 0)
            throw new MissingDataException("The list named " + variableName + " is empty.");
    }

    public static void assertNotNullOrEmpty(String variableName, Collection<?> objects) {
        assertNotNull(variableName, objects);
        assertNotEmpty(variableName, objects);
    }

    public static void assertSameSize (
        String listAName,
        Collection<?> listA,
        String listBName,
        Collection<?> listB
    ) {
        assertNotNullOrEmpty(listAName, listA);
        assertNotNullOrEmpty(listBName, listB);

        if (listA.size() != listB.size())
            throw new ImbalancedCollectionsException ("The size of the lists should be equal (" + listAName + ".size: "
                + listA.size() + ", " + listB + ".size: " + listB.size());
    }

    /**
     * A helper method that is used to convert the itemEvent into a series.
     *
     * @todo Move this to an OMM Helper class.
     */
    public static OMMSeries getSeries (OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg();

        OMMData data = msg.getPayload();

        short type = data.getType();

        if (type != OMMTypes.SERIES)
            throw new ConversionFailedException("The data should be of " +
                "type SERIES however it is of type " + OMMTypes.toString(type));

        OMMSeries series = (OMMSeries) data;

        return series;
    }

    public static void sleep (long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException("Unable to sleep for " + millis +
                " millis.", interruptedException);
        }
    }

    /**
     * @return The cache associated with the given handle.
     */
    public static Session getSession (
        Map<Handle, Session> sessionCache,
        Handle handle
    ) {
        assertNotNull("handle", handle);

        Session session = sessionCache.get(handle);

        assertNotNull ("session", session);

        return session;
    }

    /**
     * Method gets the handle from the event and then returns the session
     * associated with that handle.
     */
    public static Session getSession (
        Map<Handle, Session> sessionCache,
        Event event
    ) {
        assertNotNull("event", event);

        Handle handle = event.getHandle();

        return getSession(sessionCache, handle); 
    }

    /**
     * Method gets the event from the message payload and then calls the
     * {@link #getSession(Event)}.
     */
    public static Session getSession (
        Map<Handle, Session> sessionCache,
        Message<Event> message
    ) {

        assertNotNull("message", message);

        Event event = message.getPayload();

        return getSession (sessionCache, event);
    }
}
