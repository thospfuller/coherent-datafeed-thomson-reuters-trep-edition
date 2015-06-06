package com.coherentlogic.coherent.datafeed.factories;

import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * Factory class for creating a single instance of a {@link FieldDictionary}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class BasicFieldDictionaryFactory implements Factory<FieldDictionary> {

    private final FieldDictionary dictionary;

    public BasicFieldDictionaryFactory () {
        this(FieldDictionary.create());
    }

    public BasicFieldDictionaryFactory (FieldDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public FieldDictionary getInstance() {
        return dictionary;
    }
}
