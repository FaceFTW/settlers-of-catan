package catan.gui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LangUtils {
	private static ResourceBundle bundle = ResourceBundle.getBundle("base", Locale.getDefault());

	protected LangUtils() {
	}

	public static String getString(String key) {
		return bundle.getString(key);
	}

	public static String getString(String key, Object... args) {
		return MessageFormat.format(bundle.getString(key), args);
	}
}
