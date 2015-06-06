package com.coherentlogic.coherent.datafeed.factories;

import java.util.prefs.Preferences;

import com.coherentlogic.coherent.datafeed.factories.Factory;

/**
 * Factory class for the {@link java.util.prefs.Preferences} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PreferencesFactory implements Factory<Preferences> {

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
