package com.coherentlogic.coherent.datafeed.adapters;

import static com.coherentlogic.coherent.datafeed.factories.MethodMapFactory.analyze;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.FatalRuntimeException;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMsg;

/**
 * An adapter that converts the OMMMsg into an instance of {@link RFABean}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RFABeanAdapter<T extends RFABean> {

    private static final Logger log =
        LoggerFactory.getLogger(RFABeanAdapter.class);

    private final Factory<T> rfaBeanFactory;

    private final FieldDictionary fieldDictionary;

    private final Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>>
        fieldEntryAdapters;

    private final Map<String, Method> methodMap;

    public RFABeanAdapter (
        Factory<T> rfaBeanFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {
        this (
            rfaBeanFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            rfaBeanClass
        );
    }

    /**
     * 
     * @param rfaBeanFactory
     * @param fieldDictionary
     * @param fieldEntryAdapters
     * @param methodMap
     * @param rfaBeanClass Analyzed in order to determine which setter methods
     *  have been annotated with {@link @RFAType} and {@link @Adapt}
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public RFABeanAdapter (
        Factory<T> rfaBeanFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {
        this.rfaBeanFactory = rfaBeanFactory;
        this.fieldDictionary = fieldDictionary;
        this.fieldEntryAdapters = fieldEntryAdapters;
        this.methodMap = methodMap;

        analyze(rfaBeanClass, methodMap);
    }

    public T adapt (OMMMsg ommMsg) {

        T rfaBean = rfaBeanFactory.getInstance();

        adapt (ommMsg, rfaBean);

        return rfaBean;
    }

    /**
     * This variant of the <i>adapt</i> method is used for updating an existing
     * marketPrice bean.
     *
     * @param ommMsg
     * @param rfaBean
     */
    public void adapt (OMMMsg ommMsg, T rfaBean) {

        if (ommMsg.has(OMMMsg.HAS_ATTRIB_INFO)) {
            OMMAttribInfo attribInfo = ommMsg.getAttribInfo();
            toRFABean (attribInfo, rfaBean);
        } else {
            log.debug("The ommMsg does not have attribInfo.");
        }

        OMMData data = ommMsg.getPayload();

        toRFABean (data, rfaBean);
    }

    void toRFABean (
        OMMAttribInfo attribInfo,
        T rfaBean) {

        Integer id = getId (attribInfo);
        Integer filter = getFilter(attribInfo);
        String serviceName = getServiceName (attribInfo);
        String name = getName (attribInfo);
        Integer serviceId = getServiceId (attribInfo);
        Short nameType =  getNameType (attribInfo);

        rfaBean.setId(id);
        rfaBean.setFilter(filter);
        rfaBean.setServiceName(serviceName);
        rfaBean.setName(name);
        rfaBean.setServiceId(serviceId);
        rfaBean.setNameType(nameType);
    }

    /**
     * @todo Unit test this method.
     */
    void toRFABean (OMMData data, T t) {

      if (data != null) {

          OMMFieldList fieldList = (OMMFieldList) data;

          short dictId = fieldList.getDictId();

          log.debug("dictId: " + dictId);

          Iterator<OMMFieldEntry> iterator = 
              (Iterator<OMMFieldEntry>) fieldList.iterator();

          while (iterator.hasNext ()) {
                OMMFieldEntry fieldEntry = iterator.next();

                short fieldId = fieldEntry.getFieldId();
                FidDef fidDef = fieldDictionary.getFidDef(fieldId);

                // TODO: Note that if the FidDef is null, this is likely to be
                //       due to the dictionary not being loaded properly -- we
                //       should add a unit test to check for this.

                if (fidDef == null) {
                    log.warn ("The fidDef is null for the dictionary with " +
                        "the dictId " + dictId + ".");
                } else {

                    String name = fidDef.getName();

                    log.debug("Next fieldEntry details include fieldId: " +
                        fieldId + ", name: " + name + ", longName: " +
                        fidDef.getLongName() + ", ommType: " +
                        fidDef.getOMMType() + ", t: " + t);

                    Method method = methodMap.get(name);

                    if (method != null) {
                        try {
                            if (!method.isAnnotationPresent(Adapt.class))
                                throw new FatalRuntimeException("The method " +
                                    method + " is missing an " +
                                    Adapt.class.getName() + " annotation.");

                            Adapt adapt = method.getAnnotation(Adapt.class);

                            Class<? extends OMMFieldEntryAdapter
                                <? extends OMMData>> usingAdapter =
                                    adapt.using();

                            OMMFieldEntryAdapter<? extends OMMData>
                                fieldEntryAdapter = fieldEntryAdapters.get(
                                    usingAdapter);

                            Class<?>[] parameterTypes = method.getParameterTypes();

                            if (parameterTypes == null
                                || parameterTypes.length != 1)
                                throw new FatalRuntimeException("The " +
                                    "parameterTypes length should be one however " +
                                    "it appears to be either null or an invalid " +
                                    "length; parameterTypes: " +
                                    ToStringBuilder.reflectionToString(
                                        parameterTypes));

                            Class<?> type = parameterTypes[0];

                            Object value = fieldEntryAdapter.adapt(
                                fieldEntry, type);

                            method.invoke(t, value);
                        } catch (Throwable thrown) {
                            throw new FatalRuntimeException(
                                "The call to the method under the key " + name +
                                " failed for entry " + fieldEntry, thrown);
                        }
                    }
                }
            }
        }
    }

    Integer getId (OMMAttribInfo attribInfo) {
        Integer result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_ID))
            result = attribInfo.getId();

        return result;
    }

    String getServiceName (OMMAttribInfo attribInfo) {

        String result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_SERVICE_NAME))
            result = attribInfo.getServiceName();

        return result;
    }

    Integer getFilter (OMMAttribInfo attribInfo) {

        Integer result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_FILTER))
            result = attribInfo.getFilter();

        return result;
    }

    String getName (OMMAttribInfo attribInfo) {
        String result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_NAME))
            result = attribInfo.getName();

        return result;
    }

    Integer getServiceId (OMMAttribInfo attribInfo) {
        Integer result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_SERVICE_ID))
            result = attribInfo.getServiceID();

        return result;
    }

    /**
     * Method gets the nameType from the attribInfo object, converts the value
     * to a Short and returns the result; null is returned when the attribInfo
     * object does not have a name type value.
     */
    Short getNameType (OMMAttribInfo attribInfo) {
        Short result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_NAME_TYPE))
            result = attribInfo.getNameType();

        return result;
    }

    public Factory<T> getRFABeanFactory() {
        return rfaBeanFactory;
    }
}
