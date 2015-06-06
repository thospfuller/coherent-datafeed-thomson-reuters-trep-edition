package com.coherentlogic.coherent.datafeed.factories;

import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;

import static com.coherentlogic.coherent.datafeed.misc.Constants.
    RPACKAGE_PATH_KEY;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the {@link RPackageDictionaryFactory} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RPackageDictionaryFactoryTest {

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testGetRDMFieldDictionaryPathWithNullPkgPath() {
        RPackageDictionaryFactory.getRDMFieldDictionaryPath(null);
    }

    @Test
    public void testGetRDMFieldDictionaryPath() {
        RPackageDictionaryFactory.getRDMFieldDictionaryPath("");
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testGetEnumFieldDictionaryPathWithNullPkgPath() {
        RPackageDictionaryFactory.getEnumFieldDictionaryPath(null);
    }

    @Test
    public void testGetEnumFieldDictionaryPath() {
        RPackageDictionaryFactory.getEnumFieldDictionaryPath("");
    }

    @Test
    public void testDefaultCtor () {

        System.setProperty(RPACKAGE_PATH_KEY, "src/test/resources/");

        RPackageDictionaryFactory factory = new RPackageDictionaryFactory ();

        factory.start();

        System.clearProperty(RPACKAGE_PATH_KEY);

        assertNotNull(factory.getInstance());
    }
}
