package com.coherentlogic.coherent.datafeed.factories;

import java.util.prefs.Preferences;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * Factory class for the {@link java.util.prefs.Preferences} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PreferencesFactory implements TypedFactory<Preferences> {

    private final Preferences preferences;

    public PreferencesFactory(String pkg) throws ClassNotFoundException {
        this (Class.forName(pkg));
    }

    public PreferencesFactory(Class<?> pkg) {
        this (Preferences.systemNodeForPackage(pkg));
    }

    public PreferencesFactory(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Preferences getInstance() {
        return preferences;
    }
}
