package com.coherentlogic.coherent.datafeed.factories;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_RELATIVE_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ENUM;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ENUM_TYPE_DEF;
import static com.coherentlogic.coherent.datafeed.misc.Constants.RDM;
import static com.coherentlogic.coherent.datafeed.misc.Constants.RDM_FIELD_DICTIONARY;

import java.io.File;
import java.net.URL;

import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * A factory that loads the dependencies required by the dictionary via the
 * ClassLoader as resources.
 *
 * @todo This does not appear to be able to load the dictionary files if they're
 *  embedded in the jar, so we'll likely need to point them to the file's
 *  external location.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultFieldDictionaryFactory extends AbstractDictionaryFactory {

    private final FieldDictionary dictionary;

    public DefaultFieldDictionaryFactory () {
        dictionary = FieldDictionary.create();

        ClassLoader defaultClassLoader =
            DefaultFieldDictionaryFactory.class.getClassLoader();

        URL rdmDictionaryURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + RDM_FIELD_DICTIONARY);

        URL enumTypeDefURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + ENUM_TYPE_DEF);

        String rdmFieldDictionaryPath = rdmDictionaryURL.getFile(),
            enumFieldDictionaryPath = enumTypeDefURL.getFile();

        loadDictionary(
            dictionary,
            rdmFieldDictionaryPath,
            enumFieldDictionaryPath);
    }

    public DefaultFieldDictionaryFactory (
        String rdmFieldDictionaryPath, String enumFieldDictionaryPath) {
        this (
            FieldDictionary.create(),
            rdmFieldDictionaryPath,
            enumFieldDictionaryPath);
    }

    public DefaultFieldDictionaryFactory (FieldDictionary dictionary,
        String rdmFieldDictionaryPath, String enumFieldDictionaryPath) {

        this.dictionary = dictionary;

        ClassLoader defaultClassLoader =
            DefaultFieldDictionaryFactory.class.getClassLoader();

        URL rdmDictionaryURL = defaultClassLoader.getResource(
            rdmFieldDictionaryPath);

        checkURL (rdmDictionaryURL, rdmFieldDictionaryPath, RDM);

        URL enumTypeDefURL =
            defaultClassLoader.getResource(enumFieldDictionaryPath);

        checkURL (enumTypeDefURL, enumFieldDictionaryPath, ENUM);

        String rdmFieldDictionaryFile = rdmDictionaryURL.getFile(),
            enumFieldDictionaryFile = enumTypeDefURL.getFile();

        loadDictionary(
            dictionary,
            rdmFieldDictionaryFile,
            enumFieldDictionaryFile);
    }

    /**
     */
    static void checkURL (
        URL dictionaryURL,
        String dictionaryPath,
        String dictionaryName
    ) {
        File file = new File (dictionaryPath);

        if (dictionaryURL == null)
            throw new ApplicationInitializationFailedException(
                "The " + dictionaryName + " dictionary located under the " +
                "path '" + dictionaryPath +
                "' could not be found (absolute path: " +
                file.getAbsolutePath() + ").");
    }

    public void start () {
        // Does nothing, however this is helpful because we'll avoid a problem
        // in the app context where the FileBased loader has a start method and
        // this doesn't.
    }

    @Override
    public FieldDictionary getInstance() {
        return dictionary;
    }
}
