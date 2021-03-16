package org.dongx.configuration.demo;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class PreferencesDemo {

	public static void main(String[] args) throws BackingStoreException {
		Preferences userPreferences = Preferences.userRoot();
		userPreferences.put("my-key", "hello-world");
		userPreferences.flush();
		System.out.println(userPreferences.get("my-key", null));
	}
}
