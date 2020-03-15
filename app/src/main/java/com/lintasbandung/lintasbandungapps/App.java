package com.lintasbandung.lintasbandungapps;

import android.app.Application;

import com.lintasbandung.lintasbandungapps.data.AppState;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppState.getInstance().initSharedPrefs(this);
    }
}
