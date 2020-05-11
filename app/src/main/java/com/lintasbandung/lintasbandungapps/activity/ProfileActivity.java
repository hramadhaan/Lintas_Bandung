package com.lintasbandung.lintasbandungapps.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

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
        namaDepan.setEnabled(false);
        namaBelakang = findViewById(R.id.profil_namaAkhir);
        namaBelakang.setText(appState.getUser().getLastName());
        namaBelakang.setEnabled(false);
        email = findViewById(R.id.profil_email);
        email.setText(appState.getUser().getEmail());
        email.setEnabled(false);
        noTelp = findViewById(R.id.profil_noTelp);
        noTelp.setText(appState.getUser().getPhone());
        password = findViewById(R.id.profil_password);

        button = findViewById(R.id.profil_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noTelp.getText().toString().equals("")) {
                    showInfoToast("Harap Masukkan No. Telepon Anda");
                } else {
                    saveUser(appState.getUser().getId(), namaDepan.getText().toString(),
                            namaBelakang.getText().toString(), email.getText().toString(),
                            noTelp.getText().toString());
                }
            }
        });
    }

    private void saveUser(String id, String namaDepan, String namaBelakang, String email, String noTelp) {
        Call<Status> statusCall = apiService.setEditUser(id, namaDepan, namaBelakang, email, noTelp);
        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    finish();
                    appState.logout();
                } else {
                    showInfoToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                showErrorToast(t.getMessage());
            }
        });
    }

    private void showErrorToast(String message) {
        SweetToast.error(ProfileActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(ProfileActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(ProfileActivity.this, message, 2200);
    }
}
