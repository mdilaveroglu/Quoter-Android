package co.dilaver.quoter.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class SharedPrefStorage {

    private static final String USER_PREFS = "USER_PREFS";
    private final SharedPreferences appSharedPrefs;
    private final SharedPreferences.Editor prefsEditor;
    private final String savedQuotes = "savedQuotes";
    private final String qodText = "qodText";
    private final String qodAuthor = "qodAuthor";
    private final String qodFont = "qodFont";
    private final String qodColor = "qodColor";

    public SharedPrefStorage(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getSavedQuotes() {
        return appSharedPrefs.getString(savedQuotes, "empty");
    }

    public void setSavedQuotes(String quotes) {
        prefsEditor.putString(savedQuotes, quotes).commit();
    }

    public String getQodText() {
        return appSharedPrefs.getString(qodText, "empty");
    }

    public void setQodText(String text) {
        prefsEditor.putString(qodText, text).commit();
    }

    public String getQodAuthor() {
        return appSharedPrefs.getString(qodAuthor, "empty");
    }

    public void setQodAuthor(String author) {
        prefsEditor.putString(qodAuthor, author).commit();
    }

    public String getQodFont() {
        return appSharedPrefs.getString(qodFont, "Exo-Regular.otf");
    }

    public void setQodFont(String font) {
        prefsEditor.putString(qodFont, font).commit();
    }

    public int getQodColor() {
        return appSharedPrefs.getInt(qodColor, Color.WHITE);
    }

    public void setQodColor(int color) {
        prefsEditor.putInt(qodColor, color).commit();
    }

}
