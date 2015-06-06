package com.coherentlogic.coherent.datafeed.factories;

import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * Factory class that loads the dictionary from the network juxtaposed with more
 * primitive implementation such as the {@link DefaultFieldDictionaryFactory}
 * which loads the dictionary via a file.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AdvancedFieldDictionaryFactory extends AbstractDictionaryFactory {

    @Override
    public FieldDictionary getInstance() {
        throw new RuntimeException("This method has not been implemented.");
    }

    @Override
    public void start() {
    }
}
