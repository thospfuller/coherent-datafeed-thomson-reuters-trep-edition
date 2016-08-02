package com.coherentlogic.coherent.datafeed.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.data.model.core.lifecycle.Startable;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.reuters.rfa.dictionary.DictionaryException;
import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * A base class for dictionary factories.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractDictionaryFactory
    implements TypedFactory<FieldDictionary>, Startable {

    private static final Logger log =
        LoggerFactory.getLogger(AbstractDictionaryFactory.class);

    /**
     * Method attempts to load the dictionary using the paths provided and
     * throws a runtime exception if this fails.
     */
    protected void loadDictionary(
        FieldDictionary dictionary,
        String rdmFieldDictionaryPath,
        String enumFieldDictionaryPath
    ) {

        log.info("loadDictionary: method begins; dictionary: " + dictionary +
            ", rdmFieldDictionaryPath: " + rdmFieldDictionaryPath +
            ", enumFieldDictionaryPath: " + enumFieldDictionaryPath);

        try {
            FieldDictionary.readRDMFieldDictionary(
                dictionary, rdmFieldDictionaryPath);
        } catch (DictionaryException dictionaryException) {
            throw new ApplicationInitializationFailedException(
                "Unable to load the RDM file named " +
                rdmFieldDictionaryPath + ". Ensure the dictionary files are " +
                    "located in the appropriate directory.",
                    dictionaryException);
        }

        try {
            FieldDictionary.readEnumTypeDef(
                dictionary, enumFieldDictionaryPath);
        } catch (DictionaryException dictionaryException) {
            throw new ApplicationInitializationFailedException(
                "Unable to load the enum file named " +
                enumFieldDictionaryPath + ". Ensure the dictionary files are " +
                    "located in the appropriate directory.",
                    dictionaryException);
        }
        checkDictionarySize(dictionary);
    }

    /**
     * Method checks the dictionary size and throws an exception if it is zero.
     *
     * @param The dictionary, which should have been loaded prior to calling
     *  this method.
     */
    protected void checkDictionarySize(FieldDictionary dictionary){
        int dictionarySize = dictionary.size();

        if(dictionarySize == 0)
            throw new ApplicationInitializationFailedException(
                "The dictionary size is zero.");
    }
}
