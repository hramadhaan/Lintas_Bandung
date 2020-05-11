package com.lintasbandung.lintasbandungapps.awalan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.errors.ApiException;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.activity.HomePageActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.lintasbandung.lintasbandungapps.models.Token;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient googleSignInClient;
    private Button button, signIn;
    private AppState appState;
    private ApiService apiService;
    private EditText email, password;
    private RelativeLayout login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bluePrimary));
        }
        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();

        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.login_progressBar);

        button = findViewById(R.id.login_button);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        signIn = findViewById(R.id.login_signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    private void signIn() {
        signIn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String string_email = email.getText().toString();
        String string_password = password.getText().toString();

        if (string_email.matches("") && string_password.matches("")) {
            showInfoToast("Masukkan Email dan Password Anda");
            signIn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else if (string_password.matches("")) {
            showInfoToast("Masukkan Password Anda");
            signIn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else if (string_email.matches("")) {
            showInfoToast("Masukkan E-Mail");
            signIn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            closeKeyboard();
            Call<Token> tokenCall = apiService.getToken(string_email, string_password);
            tokenCall.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        String hasilToken = response.body().getToken();
                        if (hasilToken.equals("false")) {
                            showErrorToast("E-Mail atau Password anda salah");
                            signIn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            appState.setToken(hasilToken);
                            appState.setIsLoggedIn(true);
                            getUser();
                            signIn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();
                        signIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showInfoToast(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    showErrorToast(t.getMessage());
                    signIn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void showErrorToast(String message) {
        SweetToast.error(LoginActivity.this, message, 2200);
    }

    private void showInfoToast(String message) {
        SweetToast.warning(LoginActivity.this, message, 2200);
    }

    private void showSuccessToast(String message) {
        SweetToast.success(LoginActivity.this, message, 2200);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getUser() {
        apiService.getUser().enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if (response.isSuccessful()) {
                    appState.saveUser(response.body());
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showInfoToast("Informasi user tidak ada");
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                showErrorToast("Kesalahan pada server");
            }
        });
    }

    private void signUp() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignUpResult(task);
        }
    }

    private void handleSignUpResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account == null) {
                showInfoToast("Akun G-Mail tidak terdaftar");
                signOut();
            } else {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        } catch (ApiException e) {
            e.printStackTrace();
            showErrorToast(e.getMessage());
        }
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Keluar", "Revoke Account");
            }
        });
    }
}
