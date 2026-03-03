package com.culinary.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LocaleHelper {

    private static final String PREF_NAME = "culinary_prefs";
    private static final String KEY_LANGUAGE = "language";

    public static Context setLocale(Context context, String language) {
        saveLanguage(context, language);
        return updateResources(context, language);
    }

    public static Context onAttach(Context context) {
        String lang = getSavedLanguage(context);
        return setLocale(context, lang);
    }

    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String defaultLang = Locale.getDefault().getLanguage();
        // Support only en, ru, de
        if (!defaultLang.equals("ru") && !defaultLang.equals("de")) defaultLang = "en";
        return prefs.getString(KEY_LANGUAGE, defaultLang);
    }

    public static void saveLanguage(Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANGUAGE, language).apply();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}
