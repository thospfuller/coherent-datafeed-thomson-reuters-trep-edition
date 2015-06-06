package com.coherentlogic.coherent.datafeed.factories;

import static com.coherentlogic.coherent.datafeed.misc.Constants.
    DEFAULT_ENUM_FIELD_DICTIONARY_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.
    DEFAULT_RDM_FIELD_DICTIONARY_PATH;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;

/**
 * Unit test for the {@link DefaultFieldDictionaryFactory} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultFieldDictionaryFactoryTest {

    private static final String INVALID_PATH = "fee/fi/fo/fum";

    @Test
    public void constructWithValidPath() {
        new DefaultFieldDictionaryFactory(
            DEFAULT_RDM_FIELD_DICTIONARY_PATH,
            DEFAULT_ENUM_FIELD_DICTIONARY_PATH);
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void constructWithInvalidPath() {
        new DefaultFieldDictionaryFactory(
            INVALID_PATH,
            DEFAULT_ENUM_FIELD_DICTIONARY_PATH);
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void constructWithInvalidPath2() {
        new DefaultFieldDictionaryFactory(
            DEFAULT_RDM_FIELD_DICTIONARY_PATH,
            INVALID_PATH);
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void checkURLWithNullURL() {
        DefaultFieldDictionaryFactory.checkURL(null, "foo", "bar");
    }

    @Test
    public void checkURLWithValidURL() throws MalformedURLException {

        URL url = new URL ("http://www.cnn.com");

        DefaultFieldDictionaryFactory.checkURL(url, "foo", "bar");
    }
}
