package catan.gui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LangUtils {
	private static ResourceBundle bundle = ResourceBundle.getBundle("base", Locale.getDefault());
	public static final Locale[] SUPPORTED_LOCALES = {Locale.US, Locale.ITALIAN };

	protected LangUtils() {
	}

	public static String getString(String key) {
		return bundle.getString(key);
	}

	public static String getString(String key, Object... args) {
		return MessageFormat.format(bundle.getString(key), args);
	}

	public static void setLocale(Locale locale) {
		Locale.setDefault(locale);
		bundle = ResourceBundle.getBundle("base", locale);
	}

	public static String[] getSupportedLanguages() {
		String[] languages = new String[SUPPORTED_LOCALES.length];
		for (int i = 0; i < SUPPORTED_LOCALES.length; i++) {
			languages[i] = SUPPORTED_LOCALES[i].getDisplayLanguage();
		}
		return languages;
	}
}
