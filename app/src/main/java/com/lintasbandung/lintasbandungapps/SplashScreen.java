package com.lintasbandung.lintasbandungapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.lintasbandung.lintasbandungapps.awalan.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private long ms = 0, splashTime = 1200;
    private boolean splashActive = true, paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final RelativeLayout relativeLayout = findViewById(R.id.splash_relative);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused) {
                            ms = ms + 100;
                            sleep(100);
                        }
                    }
                } catch (Exception e) {

                } finally {
                    if (!isOnline()) {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "No internet connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recreate();
                            }
                        });
                        snackbar.show();
                    } else {
                        goMain();
                    }
                }
            }
        };
        thread.start();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void goMain() {
        Intent intent = new Intent(SplashScreen.this, OnBoardingScreen.class);
        startActivity(intent);
        finish();
    }
}
