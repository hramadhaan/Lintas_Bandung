package com.lintasbandung.lintasbandungapps.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppState {
    private static AppState instance;
    private static final String TOKEN_KEY = "ACCESS_TOKEN";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";

    private AppState() {
    }

    private SharedPreferences pref;

    public static AppState getInstance() {
        if (instance == null) {
            synchronized (AppState.class) {
                if (instance == null) {
                    instance = new AppState();
                }
            }
        }
        return instance;
    }

    public void initSharedPrefs(Application application) {
        pref = application.getSharedPreferences("com.lintasbandung.lintasbandungapps.SHARED_PREF", Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        pref.edit().putString(TOKEN_KEY, token).apply();
    }

    public boolean hasToken() {
        return pref.contains(TOKEN_KEY);
    }

    public String provideToken() {
        return pref.getString(TOKEN_KEY, null);
    }

    public void removeToken() {
        pref.edit().remove(TOKEN_KEY).apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(Boolean status) {
        pref.edit().putBoolean(IS_LOGGED_IN, status).apply();
    }

    public void logout() {
        removeToken();
        setIsLoggedIn(false);
    }


}
