package com.coherentlogic.coherent.datafeed.factories;

import static com.coherentlogic.coherent.datafeed.misc.Constants.
    DEFAULT_ENUM_FIELD_DICTIONARY_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.
    DEFAULT_RDM_FIELD_DICTIONARY_PATH;

import org.junit.After;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;

/**
 * Unit test for the {@link FileBasedFieldDictionaryFactory} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FileBasedFieldDictionaryFactoryTest {

    private static final String FOO = "foo", BAR = "bar";

    private FileBasedFieldDictionaryFactory factory = null;

    @After
    public void tearDown() throws Exception {
        factory = null;
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testStart1() {
        factory = new FileBasedFieldDictionaryFactory (null, BAR);
        factory.start();
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testStart2() {
        factory = new FileBasedFieldDictionaryFactory (FOO, null);
        factory.start();
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testStart3() {
        factory = new FileBasedFieldDictionaryFactory (FOO, BAR);
        factory.start();
    }

    /**
     * In this case both files exist and hence we expect the test to pass.
     */
    public void testStart4() {
        factory = new FileBasedFieldDictionaryFactory (
            DEFAULT_RDM_FIELD_DICTIONARY_PATH,
            DEFAULT_ENUM_FIELD_DICTIONARY_PATH
        );
        factory.start();
    }
}
