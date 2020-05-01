package com.lintasbandung.lintasbandungapps.activity.angkot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lintasbandung.lintasbandungapps.R;

public class ScanAngkotActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_angkot);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("id");

        showToast(id);

    }

    private void showToast(String message) {
        Toast.makeText(ScanAngkotActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
