package com.lintasbandung.lintasbandungapps.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class ChangePasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText password;
    private Button changePassword;
    private ApiService apiService;
    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();
        final String id = appState.getUser().getId();

        toolbar = findViewById(R.id.changePassword_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        password = findViewById(R.id.changePassword_password);
        changePassword = findViewById(R.id.changePassword_button);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("")) {
                    showInfoToast("Isi Password Baru Anda");
                } else {
                    changen(id, password.getText().toString());
                }
            }
        });
    }

    private void changen(String id, String password) {
        Call<Status> editPasssword = apiService.changePassword(id, password);
        editPasssword.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        showSuccessToast("Password Anda telah diganti, harap Login kembali");
                        finish();
                        appState.logout();
                    } else {
                        showInfoToast("Coba ulangi lagi");
                        Log.d("Password", response.body().getStatus());
                    }
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
        SweetToast.error(ChangePasswordActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(ChangePasswordActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(ChangePasswordActivity.this, message, 2200);
    }
}
