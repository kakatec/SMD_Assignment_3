package com.example.smd_assignment_3.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_THEME = "dark_mode";

    private SharedPreferences prefs;

    public SharedPreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserInfo(String name, String email) {
        prefs.edit().putString(KEY_NAME, name).putString(KEY_EMAIL, email).apply();
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public void setDarkMode(boolean isDark) {
        prefs.edit().putBoolean(KEY_THEME, isDark).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(KEY_THEME, false);
    }
}