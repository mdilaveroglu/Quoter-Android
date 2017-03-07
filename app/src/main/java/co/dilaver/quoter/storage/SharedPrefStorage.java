package co.dilaver.quoter.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import co.dilaver.quoter.models.Quote;

public class SharedPrefStorage {

    private static final String USER_PREFS = "USER_PREFS";
    private static final String SAVED_QUOTES = "savedQuotes";
    private static final String QOD_TEXT = "qodText";
    private static final String QOD_AUTHOR = "qodAuthor";
    private static final String QOD_FONT = "qodFont";
    private static final String QOD_COLOR = "qodColor";

    private final SharedPreferences appSharedPrefs;
    private final SharedPreferences.Editor prefsEditor;

    public SharedPrefStorage(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getSavedQuotes() {
        return appSharedPrefs.getString(SAVED_QUOTES, Quote.EMPTY);
    }

    public void setSavedQuotes(String quotes) {
        prefsEditor.putString(SAVED_QUOTES, quotes).commit();
    }

    public Quote getQod() {
        return new Quote(appSharedPrefs.getString(QOD_TEXT, Quote.EMPTY), appSharedPrefs.getString(QOD_AUTHOR, Quote.EMPTY));
    }

    public void setQodText(String text) {
        prefsEditor.putString(QOD_TEXT, text).commit();
    }

    public void setQodAuthor(String author) {
        prefsEditor.putString(QOD_AUTHOR, author).commit();
    }

    public String getQodFont() {
        return appSharedPrefs.getString(QOD_FONT, "Exo-Regular.otf");
    }

    public void setQodFont(String font) {
        prefsEditor.putString(QOD_FONT, font).commit();
    }

    public int getQodColor() {
        return appSharedPrefs.getInt(QOD_COLOR, Color.WHITE);
    }

    public void setQodColor(int color) {
        prefsEditor.putInt(QOD_COLOR, color).commit();
    }

}
