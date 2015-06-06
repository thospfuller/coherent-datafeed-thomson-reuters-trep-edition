package com.coherentlogic.coherent.datafeed.factories;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;
import com.reuters.rfa.dictionary.DictionaryException;
import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * A factory that loads the field dictionary from the {@link #rdmFilename}
 * provided.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FileBasedFieldDictionaryFactory
    extends AbstractDictionaryFactory {

    private static final Logger log =
        LoggerFactory.getLogger(FileBasedFieldDictionaryFactory.class);

    private final String rdmFilename, enumFilename;

    private final FieldDictionary dictionary;

    public FileBasedFieldDictionaryFactory (
        String rdmFieldDictionaryPath,
        String enumFieldDictionaryPath
    ) {
        dictionary = FieldDictionary.create();
        this.rdmFilename = rdmFieldDictionaryPath;
        this.enumFilename = enumFieldDictionaryPath;
    }

    public void start () {

        File file = new File (".");

        log.info("rdmFilename: " + rdmFilename + ", enumFilename: "
            + enumFilename + ", current path: " + file.getAbsolutePath());

        try {
            FieldDictionary.readRDMFieldDictionary(dictionary, rdmFilename);
        } catch (DictionaryException dictionaryException) {
            throw new ApplicationInitializationFailedException(
                "Unable to load the RDM file named '" + rdmFilename + "'.",
                dictionaryException);
        }

        try {
            FieldDictionary.readEnumTypeDef(dictionary, enumFilename);
        } catch (DictionaryException dictionaryException) {
            throw new ApplicationInitializationFailedException(
                "Unable to load the enum file named '" + enumFilename + "'.",
                dictionaryException);
        }
    }

    @Override
    public FieldDictionary getInstance() {
        return dictionary;
    }
}
