package co.dilaver.quoter.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefStorage {

    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String savedQuotes = "savedQuotes";
    private String qodText = "qodText";
    private String qodAuthor = "qodAuthor";

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

}
