package com.lintasbandung.lintasbandungapps.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.awalan.LoginActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText namaDepan, namaBelakang, email, noTelp, password;
    private ApiService apiService;
    private AppState appState;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();
        toolbar = findViewById(R.id.profil_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        namaDepan = findViewById(R.id.profil_namaDepan);
        namaDepan.setText(appState.getUser().getFirstName());
        namaBelakang = findViewById(R.id.profil_namaAkhir);
        namaBelakang.setText(appState.getUser().getLastName());
        email = findViewById(R.id.profil_email);
        email.setText(appState.getUser().getEmail());
        noTelp = findViewById(R.id.profil_noTelp);
        noTelp.setText(appState.getUser().getPhone());
        password = findViewById(R.id.profil_password);
//        password.setText(appState.getUser().getPassword());

        button = findViewById(R.id.profil_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = "";
                pass = password.getText().toString();
                saveUser(appState.getUser().getId(), namaDepan.getText().toString(), namaBelakang.getText().toString(), email.getText().toString(), noTelp.getText().toString(), pass);
            }
        });
    }

    private void saveUser(String id, String namaDepan, String namaBelakang, String email, String noTelp, String password) {
        Call<Status> statusCall = apiService.setEditUser(id, namaDepan, namaBelakang, email, noTelp);
        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    finish();
                    appState.logout();
                } else {
                    showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
