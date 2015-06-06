package com.coherentlogic.coherent.datafeed.services;

import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * A service (proxy) for the RFA Dictionary used by this application.
 *
 * WARNING: If you're using the FileBasedFieldDictionaryFactory don't forget
 *  to assign the init method! DefaultFieldDictionaryFactory
 *
 *  Use the FileBasedFieldDictionaryFactory when running this outside of
 *  Eclipse, however note that calling mvn package will fail unless you've
 *  copied the data directory into the module's root folder.
 *  No ctor args for:
 *  DefaultFieldDictionaryFactory works when running Example in Eclipse.
 *  RPackageDictionaryFactory is used when deploying in R.
 *
 *  <bean id="fieldDictionaryFactory"
 *   class="com.coherentlogic.coherent.datafeed.factories.
 *       DefaultFieldDictionaryFactory"
 *   init-method="start">
 *  </bean>
 *
 *  <bean id="fieldDictionary"
 *   class="com.reuters.rfa.dictionary.FieldDictionary"
 *   factory-bean="fieldDictionaryFactory"
 *   factory-method="getInstance"/>
 *
 *  <bean id="dictionaryService"
 *   class="com.coherentlogic.coherent.datafeed.services.FieldDictionaryHolder">
 *      <constructor-arg name="fieldDictionary" ref="fieldDictionary"/>
 *  </bean>
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FieldDictionaryHolder {

    private final FieldDictionary fieldDictionary;

    public FieldDictionaryHolder (FieldDictionary fieldDictionary) {
        this.fieldDictionary = fieldDictionary;
    }

    public FieldDictionary getFieldDictionary () {
        return fieldDictionary;
    }
}
