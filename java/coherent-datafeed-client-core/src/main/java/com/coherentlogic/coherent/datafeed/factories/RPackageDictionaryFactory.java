package com.coherentlogic.coherent.datafeed.factories;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_RELATIVE_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ENUM_TYPE_DEF;
import static com.coherentlogic.coherent.datafeed.misc.Constants.RDM_FIELD_DICTIONARY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.RPACKAGE_PATH_KEY;

import java.io.File;

import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;

/**
 * A factory class that loads the dictionary file from the path passed in as a
 * VM parameter.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RPackageDictionaryFactory extends FileBasedFieldDictionaryFactory {

    public RPackageDictionaryFactory () {
        super(
            getRDMFieldDictionaryPath (System.getProperty(RPACKAGE_PATH_KEY)),
            getEnumFieldDictionaryPath(System.getProperty(RPACKAGE_PATH_KEY))
        );
    }

    static String getRDMFieldDictionaryPath (String packagePath) {

        if (packagePath == null)
            throw new ApplicationInitializationFailedException(
                "Invalid package path: '" + packagePath + "'.");

        File path = new File (packagePath, DEFAULT_RELATIVE_PATH);

        File rdmFile = new File (path, RDM_FIELD_DICTIONARY);

        return rdmFile.getAbsolutePath();
    }

    static String getEnumFieldDictionaryPath (String packagePath) {

        if (packagePath == null)
            throw new ApplicationInitializationFailedException(
                "Invalid package path: '" + packagePath + "'.");

        File path = new File (packagePath, DEFAULT_RELATIVE_PATH);

        File rdmFile = new File (path, ENUM_TYPE_DEF);

        return rdmFile.getAbsolutePath();
    }
}
