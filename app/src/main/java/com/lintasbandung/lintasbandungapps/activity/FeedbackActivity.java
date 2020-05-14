package com.lintasbandung.lintasbandungapps.activity;

import android.os.Bundle;
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

public class FeedbackActivity extends AppCompatActivity {

    private EditText namaDepan, namaAkhir, email, feedback;
    private Button sendFeedback;
    private ApiService apiService;
    private AppState appState;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();

        toolbar = findViewById(R.id.feedback_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        namaDepan = findViewById(R.id.feedback_namaDepan);
        namaDepan.setText(appState.getUser().getFirstName());
        namaAkhir = findViewById(R.id.feedback_namaAkhir);
        namaAkhir.setText(appState.getUser().getLastName());
        email = findViewById(R.id.feedback_email);
        email.setText(appState.getUser().getEmail());
        feedback = findViewById(R.id.feedback_isi);
        sendFeedback = findViewById(R.id.feedback_button);

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(namaDepan.getText().toString(), namaAkhir.getText().toString(), email.getText().toString(), feedback.getText().toString());
            }
        });

    }

    private void send(String namaDepan, String namaAkhir, String email, String feedback) {
        if (feedback.equals("")) {
            showInfoToast("Harap isi feedback Anda");
        } else {
            Call<Status> setFeedback = apiService.setFeedback(namaDepan, namaAkhir, email, feedback);
            setFeedback.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("sukses")) {
                            showSuccessToast("Terimakasih telah mengisi feedback, semoga Kami akan memberikan yang terbaik kepada Anda");
                            finish();
                        } else {
                            showInfoToast("Coba ulangi lagi");
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
    }

    private void showErrorToast(String message) {
        SweetToast.error(FeedbackActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(FeedbackActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(FeedbackActivity.this, message, 2200);
    }
}
