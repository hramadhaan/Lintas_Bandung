package com.lintasbandung.lintasbandungapps.awalan;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private EditText firstName, lastName, email, numbPhone, password;
    private Button submit;
    private ApiService apiService;
    private AppState appState;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bluePrimary));
        }

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();

        progressBar = findViewById(R.id.signUp_progressBar);
        firstName = findViewById(R.id.signUp_firstName);
        lastName = findViewById(R.id.signUp_lastName);
        email = findViewById(R.id.signUp_email);
        numbPhone = findViewById(R.id.signUp_phone);
        password = findViewById(R.id.signUp_password);
        submit = findViewById(R.id.signUp_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SignUpActivity.this);
        if (acct != null) {
            firstName.setText(acct.getGivenName());
            lastName.setText(acct.getFamilyName());
            email.setText(acct.getEmail());

            firstName.setEnabled(false);
            lastName.setEnabled(false);
            email.setEnabled(false);

            numbPhone.requestFocus();
        } else {
            finish();
            showToast("Tidak ada data Google Account");
        }
        signOut();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        submit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String string_firstName = firstName.getText().toString();
        String string_lastName = lastName.getText().toString();
        String string_email = email.getText().toString();
        String string_phone = numbPhone.getText().toString();
        String string_password = password.getText().toString();

        closeKeyboard();
        if (string_email.equals("") && string_firstName.equals("") && string_lastName.equals("") && string_phone.equals("") && string_password.equals("")) {
            showToast("Wajib isi data Anda");
            submit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            Call<Status> createUser = apiService.createUser(string_firstName, string_lastName, string_email, string_phone, string_password, "penumpang");
            createUser.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("sukses")) {
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            finish();
                            signOut();
                            showToast("Akun anda terdaftar, silahkan login !");
                        } else if (response.body().getStatus().equals("gagal")) {
                            showToast("Gagal mendaftarkan akun atau akun Anda telah terdaftar");
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            showToast("Terjadi kesalahan error");
                        }
                    } else {
                        showToast(response.message());
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    showToast(t.getMessage());
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(SignUpActivity.this, "Gagal Membuat Akun", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
